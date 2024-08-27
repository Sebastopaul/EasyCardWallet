package com.isitechproject.easycardwallet.screens.loyaltycards.sharedloyaltycardslist

import androidx.compose.runtime.mutableStateOf
import com.isitechproject.easycardwallet.model.User
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.CardService
import com.isitechproject.easycardwallet.model.service.LoyaltyCardService
import com.isitechproject.easycardwallet.model.service.SharedCardService
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

    fun getUser(id: String): User {
        val user = mutableStateOf(User())

        runBlocking {
            user.value = userService.getOneById(id)
        }

        return user.value
    }

    fun stopSharing(id: String) {
        launchCatching {
            sharedLoyaltyCardService.delete(id)
        }
    }
}