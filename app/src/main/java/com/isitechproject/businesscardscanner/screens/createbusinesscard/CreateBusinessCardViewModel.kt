package com.isitechproject.businesscardscanner.screens.createbusinesscard

import com.isitechproject.barcodescanner.BARCODE_DEFAULT
import com.isitechproject.barcodescanner.EASY_CARD_WALLET_MAIN_SCREEN
import com.isitechproject.easycardwallet.model.BusinessCard
import com.isitechproject.easycardwallet.model.LoyaltyCard
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.BusinessCardService
import com.isitechproject.easycardwallet.model.service.LoyaltyCardService
import com.isitechproject.easycardwallet.screens.EasyCardWalletAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CreateBusinessCardViewModel @Inject constructor(
    private val accountService: AccountService,
    private val businessCardService: BusinessCardService,
): EasyCardWalletAppViewModel(accountService) {
    val companyName = MutableStateFlow("")
    val contactFirstname = MutableStateFlow("")
    val contactLastname = MutableStateFlow("")
    val contactPhone = MutableStateFlow("")
    val contactMobile = MutableStateFlow("")
    val contactEmail = MutableStateFlow("")

    private fun buildName(): String {
        return ""
    }

    private fun registerBusinessCard(picture: String) {
        launchCatching {
            businessCardService.create(
                BusinessCard(
                    companyName = companyName.value,
                    contactFirstname = contactFirstname.value,
                    contactLastname = contactLastname.value,
                    contactPhone = contactPhone.value,
                    contactMobile = contactMobile.value,
                    contactEmail = contactEmail.value,
                    name = buildName(),
                    picture = picture,
                    uid = accountService.currentUserId
                )
            )
        }
    }

    fun updateCompanyName(newValue: String) {
        companyName.value = newValue
    }

    fun updateContactFirstname(newValue: String) {
        contactFirstname.value = newValue
    }

    fun updateContactLastname(newValue: String) {
        contactLastname.value = newValue
    }

    fun updateContactPhone(newValue: String) {
        contactPhone.value = newValue
    }

    fun updateContactMobile(newValue: String) {
        contactMobile.value = newValue
    }

    fun updateContactEmail(newValue: String) {
        contactEmail.value = newValue
    }

    fun onRegisterClick(
        cardPicture: String,
        backToMain: (String) -> Unit
    ) {
        registerBusinessCard(cardPicture)
        backToMain(EASY_CARD_WALLET_MAIN_SCREEN)
    }
}