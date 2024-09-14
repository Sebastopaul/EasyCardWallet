package com.isitechproject.easycardwallet.screens.businesscards.businesscardscreen

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
class BusinessCardViewModel @Inject constructor(
    private val accountService: AccountService,
    private val userService: UserService,
    private val businessCardService: BusinessCardService,
    private val sharedBusinessCardService: SharedBusinessCardService,
): EasyCardWalletAppViewModel(accountService) {
    val businessCard = MutableStateFlow(DEFAULT_LOYALTY_CARD)
    val emailToShare = MutableStateFlow("")

    fun initialize(businessCardId: String, restartApp: (String) -> Unit) {
        launchCatching {
            businessCard.value = (businessCardService.getOne(businessCardId) ?: DEFAULT_LOYALTY_CARD) as BusinessCard
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

    fun updateBusinessCard(name: String) {
        businessCard.value = businessCard.value.copy(
            name = name
        ).withId(businessCard.value.id)
    }

    fun saveBusinessCard(popUpScreen: () -> Unit) {
        launchCatching {
            if (businessCard.value.id == LOYALTY_CARD_DEFAULT_ID) {
                businessCardService.create(businessCard.value)
            } else {
                businessCardService.update(businessCard.value)
            }
        }

        popUpScreen()
    }

    fun deleteBusinessCard(popUpScreen: () -> Unit) {
        launchCatching {
            for (sharedBusinessCard in sharedBusinessCardService.getAllBySharedId(businessCard.value.id)) {
                sharedBusinessCardService.delete(sharedBusinessCard.id)
            }
            businessCardService.delete(businessCard.value.id)
        }

        popUpScreen()
    }

    fun deleteSharedBusinessCard(popUpScreen: () -> Unit) {
        launchCatching {
            val sharedBusinessCardToDestroy = sharedBusinessCardService.getOneBySharedId(
                businessCard.value.id,
                userService.currentUserId
            )

            sharedBusinessCardService.delete(sharedBusinessCardToDestroy.id)
        }

        popUpScreen()
    }

    fun updateEmailToShare(email: String) {
        emailToShare.value = email
    }

    fun shareBusinessCard() {
        launchCatching {
            sharedBusinessCardService.create(SharedBusinessCard(
                sharedCardId = businessCard.value.id,
                sharedUid = userService.getOneByEmail(emailToShare.value).uid,
                uid = userService.currentUserId,
            ))
        }
    }

    fun isUserProperty(): Boolean {
        return businessCard.value.uid == userService.currentUserId
    }

    companion object {
        private val DEFAULT_LOYALTY_CARD = BusinessCard(
            "My BusinessCard",
            "picture",
            "uid",
        ).withId<BusinessCard>(LOYALTY_CARD_DEFAULT_ID)
    }

}