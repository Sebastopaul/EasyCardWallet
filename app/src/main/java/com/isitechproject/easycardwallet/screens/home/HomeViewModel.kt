package com.isitechproject.easycardwallet.screens.home

import androidx.lifecycle.MutableLiveData
import com.isitechproject.easycardwallet.SPLASH_SCREEN
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
class HomeViewModel @Inject constructor(
    private val accountService: AccountService,
    private val loyaltyCardService: LoyaltyCardService
): EasyCardWalletAppViewModel() {
    fun initialize(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.currentUser.collect {user ->
                if (user == null) restartApp(SPLASH_SCREEN)
            }
        }
    }

    private val isDrawerOpen = MutableLiveData<Boolean>().apply { value = false }

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

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getLoyaltyCards(): List<LoyaltyCard> {
        val list = mutableListOf<LoyaltyCard>()

        launchCatching {
            for (loyaltyCard in loyaltyCardService.loyaltyCards.flatMapConcat { it.asFlow() }.toList()) {
                list.add(loyaltyCard)
            }
        }
        return list
    }
}