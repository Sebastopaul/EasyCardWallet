package com.isitechproject.easycardwallet.screens.loyaltycards.createloyaltycardscreen

import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.LoyaltyCardService
import com.isitechproject.easycardwallet.screens.EasyCardWalletAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateLoyaltyCardViewModel @Inject constructor(
    accountService: AccountService,
    private val loyaltyCardService: LoyaltyCardService
): EasyCardWalletAppViewModel(accountService) {

}