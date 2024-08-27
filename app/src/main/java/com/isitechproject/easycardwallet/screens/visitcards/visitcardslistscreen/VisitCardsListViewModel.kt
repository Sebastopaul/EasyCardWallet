package com.isitechproject.easycardwallet.screens.visitcards.visitcardslistscreen

import com.isitechproject.easycardwallet.LOYALTY_CARD_ID
import com.isitechproject.easycardwallet.LOYALTY_CARD_SCREEN
import com.isitechproject.easycardwallet.SCAN_LOYALTY_CARD_SCREEN
import com.isitechproject.easycardwallet.SHARED_LOYALTY_CARDS_SCREEN
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.BusinessCardService
import com.isitechproject.easycardwallet.screens.EasyCardWalletAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VisitCardsListViewModel @Inject constructor(
    accountService: AccountService,
    businessCardService: BusinessCardService,
): EasyCardWalletAppViewModel(accountService) {
    val userVisitCards = businessCardService.userCards
    val sharedVisitCards = businessCardService.sharedCards

    fun onAddClick(openScreen: (String) -> Unit) {
        openScreen(SCAN_LOYALTY_CARD_SCREEN)
    }

    fun onVisitCardClick(openScreen: (String) -> Unit, cardId: String) {
        openScreen("$LOYALTY_CARD_SCREEN?$LOYALTY_CARD_ID=${cardId}")
    }

    fun onSharedClick(openScreen: (String) -> Unit) {
        openScreen(SHARED_LOYALTY_CARDS_SCREEN)
    }
}