package com.isitechproject.barcodescanner.screens.createloyaltycard

import com.isitechproject.barcodescanner.BASE64_BARCODE_DEFAULT
import com.isitechproject.barcodescanner.EASY_CARD_WALLET_MAIN_SCREEN
import com.isitechproject.easycardwallet.model.LoyaltyCard
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.CardService
import com.isitechproject.easycardwallet.screens.EasyCardWalletAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CreateLoyaltyCardViewModel @Inject constructor(
    private val accountService: AccountService,
    private val loyaltyCardService: CardService,
): EasyCardWalletAppViewModel(accountService) {
    val loyaltyCardName = MutableStateFlow(BASE64_BARCODE_DEFAULT)

    private fun registerLoyaltyCard(data: String, barcode: String) {
        launchCatching {
            loyaltyCardService.create(
                LoyaltyCard(
                    name = loyaltyCardName.value,
                    data = data,
                    picture = barcode,
                    uid = accountService.currentUserId
                )
            )
        }
    }

    fun updateLoyaltyCardName(newValue: String) {
        loyaltyCardName.value = newValue
    }

    fun onRegisterClick(
        barcode: String,
        data: String,
        backToMain: (String) -> Unit
    ) {
        registerLoyaltyCard(data, barcode)
        backToMain(EASY_CARD_WALLET_MAIN_SCREEN)
    }
}