package com.isitechproject.barcodescanner.screens.scanloyaltycard

import android.util.Base64
import com.google.mlkit.vision.barcode.common.Barcode
import com.isitechproject.easycardwallet.model.LoyaltyCard
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.LoyaltyCardService
import com.isitechproject.easycardwallet.screens.EasyCardWalletAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScanLoyaltyCardViewModel @Inject constructor(
    private val accountService: AccountService,
): EasyCardWalletAppViewModel(accountService) {

}