package com.isitechproject.easycardwallet.screens.loyaltycards.sharedloyaltycardslist

import com.isitechproject.easycardwallet.model.User
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.LoyaltyCardService
import com.isitechproject.easycardwallet.model.service.SharedLoyaltyCardService
import com.isitechproject.easycardwallet.model.service.UserService
import com.isitechproject.easycardwallet.screens.EasyCardWalletAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SharedLoyaltyCardsListViewModel @Inject constructor(
    accountService: AccountService,
    private val userService: UserService,
    loyaltyCardService: LoyaltyCardService,
    private val sharedLoyaltyCardService: SharedLoyaltyCardService,
): EasyCardWalletAppViewModel(accountService) {
    val sharedLoyaltyCards = sharedLoyaltyCardService.currentUserSharedCards
    val loyaltyCards = loyaltyCardService.userCards
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