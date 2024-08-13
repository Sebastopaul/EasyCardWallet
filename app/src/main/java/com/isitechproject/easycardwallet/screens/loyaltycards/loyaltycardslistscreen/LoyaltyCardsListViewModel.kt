package com.isitechproject.easycardwallet.screens.loyaltycards.loyaltycardslistscreen

import android.net.Uri
import com.isitechproject.barcodescanner.BARCODE_FORMAT
import com.isitechproject.barcodescanner.BASE64_BARCODE_ARG_NAME
import com.isitechproject.barcodescanner.CREATE_LOYALTY_CARD_SCREEN
import com.isitechproject.barcodescanner.screens.scanloyaltycard.getBarcodeValue
import com.isitechproject.easycardwallet.LOYALTY_CARD_ID
import com.isitechproject.easycardwallet.LOYALTY_CARD_ID_ARG
import com.isitechproject.easycardwallet.LOYALTY_CARD_SCREEN
import com.isitechproject.easycardwallet.SCAN_LOYALTY_CARD_SCREEN
import com.isitechproject.easycardwallet.model.LoyaltyCard
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
        openScreen(SCAN_LOYALTY_CARD_SCREEN)
    }

    fun onLoyaltyCardClick(openScreen: (String) -> Unit, card: LoyaltyCard) {
        openScreen("$LOYALTY_CARD_SCREEN?$LOYALTY_CARD_ID=${card.id}")
    }
}