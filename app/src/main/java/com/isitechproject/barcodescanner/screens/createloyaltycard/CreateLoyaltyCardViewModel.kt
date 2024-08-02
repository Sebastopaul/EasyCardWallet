package com.isitechproject.barcodescanner.screens.createloyaltycard

import android.util.Base64
import com.google.mlkit.vision.barcode.common.Barcode
import com.isitechproject.barcodescanner.EASY_CARD_WALLET_MAIN_SCREEN
import com.isitechproject.easycardwallet.model.LoyaltyCard
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.LoyaltyCardService
import com.isitechproject.easycardwallet.screens.EasyCardWalletAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateLoyaltyCardViewModel @Inject constructor(
    private val accountService: AccountService,
    private val loyaltyCardService: LoyaltyCardService,
): EasyCardWalletAppViewModel(accountService) {
    private var loyaltyCardName = ""

    private fun registerLoyaltyCard(name: String, barcode: String) {
        launchCatching {
            loyaltyCardService.create(
                LoyaltyCard(
                    name = name,
                    picture = barcode,
                    uid = accountService.currentUserId
                )
            )
        }
    }

    fun updateLoyaltyCardName(newValue: String) {
        loyaltyCardName = newValue
    }

    fun onRegisterClick(
        barcode: String,
        backToMain: (String) -> Unit
    ) {
        registerLoyaltyCard(loyaltyCardName, barcode)
        backToMain(EASY_CARD_WALLET_MAIN_SCREEN)
    }
}