package com.isitechproject.easycardwallet.screens.loyaltycards.sharedloyaltycardslist

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.asLiveData
import com.isitechproject.easycardwallet.model.LoyaltyCard
import com.isitechproject.easycardwallet.model.SharedLoyaltyCard
import com.isitechproject.easycardwallet.model.User
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.LoyaltyCardService
import com.isitechproject.easycardwallet.model.service.SharedLoyaltyCardService
import com.isitechproject.easycardwallet.model.service.UserService
import com.isitechproject.easycardwallet.screens.EasyCardWalletAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class SharedLoyaltyCardsListViewModel @Inject constructor(
    accountService: AccountService,
    private val userService: UserService,
    private val loyaltyCardService: LoyaltyCardService,
    private val sharedLoyaltyCardService: SharedLoyaltyCardService,
): EasyCardWalletAppViewModel(accountService) {
    val sharedLoyaltyCards = sharedLoyaltyCardService.currentUserSharedLoyaltyCards
    val loyaltyCards = loyaltyCardService.userLoyaltyCards
    private val users = mutableListOf<User>()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun initialize(restartApp: (String) -> Unit) {
        val userIds = mutableListOf<String>()

        Log.d("TEST_INIT", "PASS INIT")
        sharedLoyaltyCards.flatMapLatest { list ->
            list.forEach {
                userIds.add(it.sharedUid)
            }
            flow<SharedLoyaltyCard> { list }
        }

        launchCatching {
            userService.getAllInIdList(userIds).forEach { users.add(it) }
        }

        super.initialize(restartApp)
    }

    fun getUserEmail(id: String): String {
        return users.first { it.id == id }.email
    }

    fun stopSharing(id: String) {
        launchCatching {
            sharedLoyaltyCardService.delete(id)
        }
    }
}