package com.isitechproject.easycardwallet.screens.loyaltycards.loyaltycardslistscreen

import com.isitechproject.easycardwallet.LOYALTY_CARD_DEFAULT_ID
import com.isitechproject.easycardwallet.LOYALTY_CARD_ID
import com.isitechproject.easycardwallet.LOYALTY_CARD_SCREEN
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.LoyaltyCardService
import com.isitechproject.easycardwallet.screens.EasyCardWalletAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoyaltyCardsListViewModel @Inject constructor(
    private val accountService: AccountService,
    private val loyaltyCardService: LoyaltyCardService
): EasyCardWalletAppViewModel(accountService) {
    val loyaltyCards = loyaltyCardService.loyaltyCards

    fun onAddClick(openScreen: (String) -> Unit) {
        openScreen("$LOYALTY_CARD_SCREEN?$LOYALTY_CARD_ID=$LOYALTY_CARD_DEFAULT_ID")
    }

    // TODO: place this in a settings screen
    fun onDeleteAccountCLick() {
        launchCatching {
            accountService.deleteAccount()
        }
    }
}