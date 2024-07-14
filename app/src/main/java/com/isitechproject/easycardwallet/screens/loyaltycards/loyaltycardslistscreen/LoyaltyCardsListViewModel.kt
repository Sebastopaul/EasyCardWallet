package com.isitechproject.easycardwallet.screens.loyaltycards.loyaltycardslistscreen

import com.isitechproject.easycardwallet.CREATE_LOYALTY_CARD_SCREEN
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.LoyaltyCardService
import com.isitechproject.easycardwallet.screens.EasyCardWalletAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoyaltyCardsListViewModel @Inject constructor(
    accountService: AccountService,
    loyaltyCardService: LoyaltyCardService,
): EasyCardWalletAppViewModel(accountService) {
    val loyaltyCards = loyaltyCardService.loyaltyCards

    fun onAddClick(openScreen: (String) -> Unit) {
        openScreen(CREATE_LOYALTY_CARD_SCREEN)
    }
}