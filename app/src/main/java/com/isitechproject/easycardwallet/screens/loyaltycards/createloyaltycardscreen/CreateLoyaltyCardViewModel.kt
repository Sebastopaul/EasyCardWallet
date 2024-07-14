package com.isitechproject.easycardwallet.screens.loyaltycards.createloyaltycardscreen

import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.barcode.common.Barcode.BarcodeFormat
import com.isitechproject.easycardwallet.LOYALTY_CARD_DEFAULT_ID
import com.isitechproject.easycardwallet.LOYALTY_CARD_ID
import com.isitechproject.easycardwallet.LOYALTY_CARD_SCREEN
import com.isitechproject.easycardwallet.Manifest
import com.isitechproject.easycardwallet.model.LoyaltyCard
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.LoyaltyCardService
import com.isitechproject.easycardwallet.screens.EasyCardWalletAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

@HiltViewModel
class CreateLoyaltyCardViewModel @Inject constructor(
    accountService: AccountService,
    private val loyaltyCardService: LoyaltyCardService
): EasyCardWalletAppViewModel(accountService) {

}