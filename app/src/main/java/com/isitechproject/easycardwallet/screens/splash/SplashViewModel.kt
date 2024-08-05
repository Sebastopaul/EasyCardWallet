package com.isitechproject.easycardwallet.screens.splash

import com.isitechproject.easycardwallet.LOYALTY_CARDS_LIST_SCREEN
import com.isitechproject.easycardwallet.SIGN_IN_SCREEN
import com.isitechproject.easycardwallet.SPLASH_SCREEN
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.screens.EasyCardWalletAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accountService: AccountService
): EasyCardWalletAppViewModel(accountService) {
    fun onAppStart(openAndPopUp: (String, String) -> Unit) {
        if (accountService.hasUser()) openAndPopUp(LOYALTY_CARDS_LIST_SCREEN, SPLASH_SCREEN)
        else openAndPopUp(SIGN_IN_SCREEN, SPLASH_SCREEN)
    }
}