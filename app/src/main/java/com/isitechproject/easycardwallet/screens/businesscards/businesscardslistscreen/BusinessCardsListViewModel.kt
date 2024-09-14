package com.isitechproject.easycardwallet.screens.businesscards.businesscardslistscreen

import com.isitechproject.easycardwallet.BUSINESS_CARD_ID
import com.isitechproject.easycardwallet.BUSINESS_CARD_SCREEN
import com.isitechproject.easycardwallet.LOYALTY_CARD_ID
import com.isitechproject.easycardwallet.LOYALTY_CARD_SCREEN
import com.isitechproject.easycardwallet.SCAN_BUSINESS_CARD_SCREEN
import com.isitechproject.easycardwallet.SCAN_LOYALTY_CARD_SCREEN
import com.isitechproject.easycardwallet.SHARED_BUSINESS_CARDS_SCREEN
import com.isitechproject.easycardwallet.SHARED_LOYALTY_CARDS_SCREEN
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.BusinessCardService
import com.isitechproject.easycardwallet.screens.EasyCardWalletAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BusinessCardsListViewModel @Inject constructor(
    accountService: AccountService,
    businessCardService: BusinessCardService,
): EasyCardWalletAppViewModel(accountService) {
    val userBusinessCards = businessCardService.userCards
    val sharedBusinessCards = businessCardService.sharedCards

    fun onAddClick(openScreen: (String) -> Unit) {
        openScreen(SCAN_BUSINESS_CARD_SCREEN)
    }

    fun onBusinessCardClick(openScreen: (String) -> Unit, cardId: String) {
        openScreen("$BUSINESS_CARD_SCREEN?$BUSINESS_CARD_ID=${cardId}")
    }

    fun onSharedClick(openScreen: (String) -> Unit) {
        openScreen(SHARED_BUSINESS_CARDS_SCREEN)
    }
}