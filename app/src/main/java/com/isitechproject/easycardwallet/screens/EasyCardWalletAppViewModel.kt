package com.isitechproject.easycardwallet.screens

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isitechproject.easycardwallet.SPLASH_SCREEN
import com.isitechproject.easycardwallet.model.service.AccountService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class EasyCardWalletAppViewModel(
    private val accountService: AccountService,
) : ViewModel() {
    private val isDrawerOpen = MutableLiveData<Boolean>().apply { value = false }

    fun launchCatching(block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                Log.e(ERROR_TAG, throwable.message.orEmpty())
            },
            block = block
        )

    open fun initialize(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.currentUser.collect {user ->
                if (user == null) restartApp(SPLASH_SCREEN)
            }
        }
    }

    fun toggleDrawer() {
        isDrawerOpen.value = !(isDrawerOpen.value ?: false)
    }

    fun onSignOutClick() {
        launchCatching {
            accountService.signOut()
        }
    }

    companion object {
        const val ERROR_TAG = "EASYCARDWALLET APP ERROR"
    }
}