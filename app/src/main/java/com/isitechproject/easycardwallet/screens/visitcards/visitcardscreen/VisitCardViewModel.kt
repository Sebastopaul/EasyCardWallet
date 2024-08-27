package com.isitechproject.easycardwallet.screens.visitcards.visitcardscreen

import android.util.Log
import com.isitechproject.easycardwallet.LOYALTY_CARD_DEFAULT_ID
import com.isitechproject.easycardwallet.SPLASH_SCREEN
import com.isitechproject.easycardwallet.model.BusinessCard
import com.isitechproject.easycardwallet.model.SharedBusinessCard
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.SharedBusinessCardService
import com.isitechproject.easycardwallet.model.service.UserService
import com.isitechproject.easycardwallet.model.service.BusinessCardService
import com.isitechproject.easycardwallet.screens.EasyCardWalletAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class VisitCardViewModel @Inject constructor(
    private val accountService: AccountService,
    private val userService: UserService,
    private val businessCardService: BusinessCardService,
    private val sharedBusinessCardService: SharedBusinessCardService,
): EasyCardWalletAppViewModel(accountService) {
    val visitCard = MutableStateFlow(DEFAULT_LOYALTY_CARD)
    val emailToShare = MutableStateFlow("")

    fun initialize(visitCardId: String, restartApp: (String) -> Unit) {
        launchCatching {
            visitCard.value = (businessCardService.getOne(visitCardId) ?: DEFAULT_LOYALTY_CARD) as BusinessCard
        }

        observeAuthenticationState(restartApp)
    }

    private fun observeAuthenticationState(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.currentUser.collect { user ->
                if (user == null) restartApp(SPLASH_SCREEN)
            }
        }
    }

    fun updateVisitCard(name: String) {
        visitCard.value = visitCard.value.copy(
            name = name
        ).withId(visitCard.value.id)
    }

    fun saveVisitCard(popUpScreen: () -> Unit) {
        launchCatching {
            if (visitCard.value.id == LOYALTY_CARD_DEFAULT_ID) {
                businessCardService.create(visitCard.value)
            } else {
                businessCardService.update(visitCard.value)
            }
        }

        popUpScreen()
    }

    fun deleteVisitCard(popUpScreen: () -> Unit) {
        launchCatching {
            businessCardService.delete(visitCard.value.id)
        }

        popUpScreen()
    }

    fun deleteSharedVisitCard(popUpScreen: () -> Unit) {
        launchCatching {
            val sharedVisitCardToDestroy = sharedBusinessCardService.getOneBySharedId(visitCard.value.id)
            Log.d("TEST_CATCHING", sharedVisitCardToDestroy.id)
            Log.d("TEST_CATCHING", sharedVisitCardToDestroy.sharedCardId)

            sharedBusinessCardService.delete(sharedVisitCardToDestroy.id)
        }

        popUpScreen()
    }

    fun updateEmailToShare(email: String) {
        emailToShare.value = email
    }

    fun shareVisitCard() {
        launchCatching {
            sharedBusinessCardService.create(SharedBusinessCard(
                sharedCardId = visitCard.value.id,
                sharedUid = userService.getOneByEmail(emailToShare.value).uid,
                uid = userService.currentUserId,
            ))
        }
    }

    fun isUserProperty(): Boolean {
        return visitCard.value.uid == userService.currentUserId
    }

    companion object {
        private val DEFAULT_LOYALTY_CARD = BusinessCard(
            "My BusinessCard",
            "picture",
            "uid",
        ).withId<BusinessCard>(LOYALTY_CARD_DEFAULT_ID)
    }

}