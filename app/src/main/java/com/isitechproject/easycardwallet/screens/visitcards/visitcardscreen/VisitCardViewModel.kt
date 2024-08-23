package com.isitechproject.easycardwallet.screens.visitcards.visitcardscreen

import android.util.Log
import com.isitechproject.easycardwallet.LOYALTY_CARD_DEFAULT_ID
import com.isitechproject.easycardwallet.SPLASH_SCREEN
import com.isitechproject.easycardwallet.model.VisitCard
import com.isitechproject.easycardwallet.model.SharedVisitCard
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.CardService
import com.isitechproject.easycardwallet.model.service.SharedCardService
import com.isitechproject.easycardwallet.model.service.SharedVisitCardService
import com.isitechproject.easycardwallet.model.service.UserService
import com.isitechproject.easycardwallet.model.service.VisitCardService
import com.isitechproject.easycardwallet.screens.EasyCardWalletAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class VisitCardViewModel @Inject constructor(
    private val accountService: AccountService,
    private val userService: UserService,
    private val visitCardService: VisitCardService,
    private val sharedVisitCardService: SharedVisitCardService,
): EasyCardWalletAppViewModel(accountService) {
    val visitCard = MutableStateFlow(DEFAULT_LOYALTY_CARD)
    val emailToShare = MutableStateFlow("")

    fun initialize(visitCardId: String, restartApp: (String) -> Unit) {
        launchCatching {
            visitCard.value = (visitCardService.getOne(visitCardId) ?: DEFAULT_LOYALTY_CARD) as VisitCard
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
                visitCardService.create(visitCard.value)
            } else {
                visitCardService.update(visitCard.value)
            }
        }

        popUpScreen()
    }

    fun deleteVisitCard(popUpScreen: () -> Unit) {
        launchCatching {
            visitCardService.delete(visitCard.value.id)
        }

        popUpScreen()
    }

    fun deleteSharedVisitCard(popUpScreen: () -> Unit) {
        launchCatching {
            val sharedVisitCardToDestroy = sharedVisitCardService.getOneBySharedId(visitCard.value.id)
            Log.d("TEST_CATCHING", sharedVisitCardToDestroy.id)
            Log.d("TEST_CATCHING", sharedVisitCardToDestroy.sharedCardId)

            sharedVisitCardService.delete(sharedVisitCardToDestroy.id)
        }

        popUpScreen()
    }

    fun updateEmailToShare(email: String) {
        emailToShare.value = email
    }

    fun shareVisitCard() {
        launchCatching {
            sharedVisitCardService.create(SharedVisitCard(
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
        private val DEFAULT_LOYALTY_CARD = VisitCard(
            "My VisitCard",
            "picture",
            "uid",
        ).withId<VisitCard>(LOYALTY_CARD_DEFAULT_ID)
    }

}