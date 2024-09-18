package com.isitechproject.businesscardscanner.screens.createbusinesscard

import com.isitechproject.barcodescanner.EASY_CARD_WALLET_MAIN_SCREEN
import com.isitechproject.easycardwallet.model.BusinessCard
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.BusinessCardService
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
    val address = MutableStateFlow("")
    val zip = MutableStateFlow("")
    val city = MutableStateFlow("")
    val contactFirstname = MutableStateFlow("")
    val contactLastname = MutableStateFlow("")
    val contactPhone = MutableStateFlow("")
    val contactMobile = MutableStateFlow("")
    val contactEmail = MutableStateFlow("")

    private fun buildName(): String {
        var name = ""

        if (companyName.value.isNotEmpty()) {
            name += companyName.value
        }
        if (contactFirstname.value.isNotEmpty()) {
            name += "${if (name.isNotEmpty()) " - " else ""}${contactFirstname.value}"
        }
        if (contactLastname.value.isNotEmpty()) {
            name += "${
                if (name.isNotEmpty())
                    if (contactFirstname.value.isNotEmpty()) " " else " - "
                else ""
            }${contactFirstname.value}"
        }
        return name
    }

    private fun registerBusinessCard() {
        launchCatching {
            businessCardService.create(
                BusinessCard(
                    companyName = companyName.value,
                    address = address.value,
                    zip = zip.value,
                    city = city.value,
                    contactFirstname = contactFirstname.value,
                    contactLastname = contactLastname.value,
                    contactPhone = contactPhone.value,
                    contactMobile = contactMobile.value,
                    contactEmail = contactEmail.value,
                    name = buildName(),
                    uid = accountService.currentUserId,
                )
            )
        }
    }

    fun initializeData(businessCard: BusinessCard) {
        companyName.value = businessCard.companyName
        address.value = businessCard.address
        zip.value = businessCard.zip
        city.value = businessCard.city
        contactFirstname.value = businessCard.contactFirstname
        contactLastname.value = businessCard.contactLastname
        contactPhone.value = businessCard.contactPhone
        contactMobile.value = businessCard.contactMobile
        contactEmail.value = businessCard.contactEmail
    }

    fun updateCompanyName(newValue: String) {
        companyName.value = newValue
    }

    fun updateAddress(newValue: String) {
        address.value = newValue
    }

    fun updateZip(newValue: String) {
        zip.value = newValue
    }

    fun updateCity(newValue: String) {
        city.value = newValue
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
        backToMain: (String) -> Unit
    ) {
        registerBusinessCard()
        backToMain(EASY_CARD_WALLET_MAIN_SCREEN)
    }
}