package com.isitechproject.barcodescanner.screens

import android.util.Base64
import com.google.mlkit.vision.barcode.common.Barcode
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
    val loyaltyCards = loyaltyCardService.loyaltyCards

    private fun handleBarcode(barcode: Barcode) {
        launchCatching {
            loyaltyCardService.create(
                LoyaltyCard(
                picture = Base64.encodeToString(barcode.rawBytes, Base64.DEFAULT),
                uid = accountService.currentUserId
            )
            )
        }
    }
}