package com.isitechproject.easycardwallet.screens.loyaltycards.loyaltycardslistscreen

import com.isitechproject.easycardwallet.LOYALTY_CARD_ID
import com.isitechproject.easycardwallet.LOYALTY_CARD_SCREEN
import com.isitechproject.easycardwallet.SCAN_LOYALTY_CARD_SCREEN
import com.isitechproject.easycardwallet.SHARED_LOYALTY_CARDS_SCREEN
import com.isitechproject.easycardwallet.model.AbstractCard
import com.isitechproject.easycardwallet.model.LoyaltyCard
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.CardService
import com.isitechproject.easycardwallet.screens.EasyCardWalletAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoyaltyCardsListViewModel @Inject constructor(
    accountService: AccountService,
    loyaltyCardService: CardService,
): EasyCardWalletAppViewModel(accountService) {
    val userLoyaltyCards = loyaltyCardService.userCards
    val sharedLoyaltyCards = loyaltyCardService.sharedCards

    fun onAddClick(openScreen: (String) -> Unit) {
        openScreen(SCAN_LOYALTY_CARD_SCREEN)
    }

    fun onLoyaltyCardClick(openScreen: (String) -> Unit, cardId: String) {
        openScreen("$LOYALTY_CARD_SCREEN?$LOYALTY_CARD_ID=${cardId}")
    }

    fun onSharedClick(openScreen: (String) -> Unit) {
        openScreen(SHARED_LOYALTY_CARDS_SCREEN)
    }
}