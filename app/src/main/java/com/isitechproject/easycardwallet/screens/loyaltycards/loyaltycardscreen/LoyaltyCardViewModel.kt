package com.isitechproject.easycardwallet.screens.loyaltycards.loyaltycardscreen

import com.isitechproject.easycardwallet.LOYALTY_CARD_DEFAULT_ID
import com.isitechproject.easycardwallet.SPLASH_SCREEN
import com.isitechproject.easycardwallet.model.LoyaltyCard
import com.isitechproject.easycardwallet.model.SharedLoyaltyCard
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.LoyaltyCardService
import com.isitechproject.easycardwallet.model.service.SharedLoyaltyCardService
import com.isitechproject.easycardwallet.model.service.UserService
import com.isitechproject.easycardwallet.screens.EasyCardWalletAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class LoyaltyCardViewModel @Inject constructor(
    private val accountService: AccountService,
    private val userService: UserService,
    private val loyaltyCardService: LoyaltyCardService,
    private val sharedLoyaltyCardService: SharedLoyaltyCardService,
): EasyCardWalletAppViewModel(accountService) {
    val loyaltyCard = MutableStateFlow(DEFAULT_LOYALTY_CARD)
    val emailToShare = MutableStateFlow("")

    fun initialize(loyaltyCardId: String, restartApp: (String) -> Unit) {
        launchCatching {
            loyaltyCard.value = loyaltyCardService.getOne(loyaltyCardId) ?: DEFAULT_LOYALTY_CARD
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

    fun updateLoyaltyCard(name: String) {
        loyaltyCard.value = loyaltyCard.value.copy(
            name = name
        ).withId(loyaltyCard.value.id)
    }

    fun saveLoyaltyCard(popUpScreen: () -> Unit) {
        launchCatching {
            if (loyaltyCard.value.id == LOYALTY_CARD_DEFAULT_ID) {
                loyaltyCardService.create(loyaltyCard.value)
            } else {
                loyaltyCardService.update(loyaltyCard.value)
            }
        }

        popUpScreen()
    }

    fun deleteLoyaltyCard(popUpScreen: () -> Unit) {
        launchCatching {
            loyaltyCardService.delete(loyaltyCard.value.id)
        }

        popUpScreen()
    }

    fun updateEmailToShare(email: String) {
        emailToShare.value = email
    }

    fun shareLoyaltyCard() {
        launchCatching {
            sharedLoyaltyCardService.create(SharedLoyaltyCard(
                loyaltyCardId = loyaltyCard.value.id,
                sharedUid = userService.getOneByEmail(emailToShare.value).uid,
                uid = userService.currentUserId,
            ))
        }
    }

    companion object {
        private val DEFAULT_LOYALTY_CARD = LoyaltyCard(
            "My LoyaltyCard",
            "defaultData",
            "picture",
            "uid"
        ).withId<LoyaltyCard>(LOYALTY_CARD_DEFAULT_ID)
    }

}