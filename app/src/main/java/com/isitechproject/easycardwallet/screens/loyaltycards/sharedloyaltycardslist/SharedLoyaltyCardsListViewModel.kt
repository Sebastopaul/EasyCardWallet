package com.isitechproject.easycardwallet.screens.loyaltycards.sharedloyaltycardslist

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
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
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SharedLoyaltyCardsListViewModel @Inject constructor(
    accountService: AccountService,
    private val userService: UserService,
    loyaltyCardService: LoyaltyCardService,
    private val sharedLoyaltyCardService: SharedLoyaltyCardService,
): EasyCardWalletAppViewModel(accountService) {
    val sharedLoyaltyCards = sharedLoyaltyCardService.currentUserSharedLoyaltyCards
    val loyaltyCards = loyaltyCardService.userLoyaltyCards
    private val users = mutableListOf<User>()

    fun initializeData() {
        runBlocking {
            sharedLoyaltyCards.first().forEach {
                val user = userService.getOneById(it.sharedUid)
                users.add(user)
            }
        }
    }

    fun getUserEmail(id: String): String {
        return users.first { it.uid == id }.email
    }

    fun stopSharing(id: String) {
        launchCatching {
            sharedLoyaltyCardService.delete(id)
        }
    }
}