package com.isitechproject.easycardwallet.screens.home

import androidx.lifecycle.MutableLiveData
import com.isitechproject.easycardwallet.SPLASH_SCREEN
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.screens.EasyCardWalletAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val accountService: AccountService
): EasyCardWalletAppViewModel() {
    fun initialize(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.currentUser.collect {user ->
                if (user == null) restartApp(SPLASH_SCREEN)
            }
        }
    }

    val isDrawerOpen = MutableLiveData<Boolean>().apply { value = false }

    fun toggleDrawer() {
        isDrawerOpen.value = !(isDrawerOpen.value ?: false)
    }

    fun onSignOutClick() {
        launchCatching {
            accountService.signOut()
        }
    }

    fun onDeleteAccountCLick() {
        launchCatching {
            accountService.deleteAccount()
        }
    }
}