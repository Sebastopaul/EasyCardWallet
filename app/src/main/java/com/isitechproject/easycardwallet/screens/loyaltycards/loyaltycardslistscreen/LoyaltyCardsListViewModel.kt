package com.isitechproject.easycardwallet.screens.loyaltycards.loyaltycardslistscreen

import androidx.compose.runtime.mutableStateOf
import com.isitechproject.easycardwallet.LOYALTY_CARD_ID
import com.isitechproject.easycardwallet.LOYALTY_CARD_SCREEN
import com.isitechproject.easycardwallet.SCAN_LOYALTY_CARD_SCREEN
import com.isitechproject.easycardwallet.SHARED_LOYALTY_CARDS_SCREEN
import com.isitechproject.easycardwallet.model.AbstractCard
import com.isitechproject.easycardwallet.model.LoyaltyCard
import com.isitechproject.easycardwallet.model.User
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.CardService
import com.isitechproject.easycardwallet.model.service.LoyaltyCardService
import com.isitechproject.easycardwallet.model.service.UserService
import com.isitechproject.easycardwallet.screens.EasyCardWalletAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class LoyaltyCardsListViewModel @Inject constructor(
    accountService: AccountService,
    val userService: UserService,
    loyaltyCardService: LoyaltyCardService,
): EasyCardWalletAppViewModel(accountService) {
    val userLoyaltyCards = loyaltyCardService.userCards
    val sharedLoyaltyCards = loyaltyCardService.sharedCards
    val currentUserId = accountService.currentUserId

    fun onAddClick(openScreen: (String) -> Unit) {
        openScreen(SCAN_LOYALTY_CARD_SCREEN)
    }

    fun onLoyaltyCardClick(openScreen: (String) -> Unit, cardId: String) {
        openScreen("$LOYALTY_CARD_SCREEN?$LOYALTY_CARD_ID=${cardId}")
    }

    fun onSharedClick(openScreen: (String) -> Unit) {
        openScreen(SHARED_LOYALTY_CARDS_SCREEN)
    }

    fun getUserPicture(id: String): String {
        val picture = mutableStateOf("")

        runBlocking {
            picture.value = userService.getOneById(id).profilePicture
        }

        return picture.value
    }
}