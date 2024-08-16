package com.isitechproject.easycardwallet.screens.loyaltycards.sharedloyaltycardslist

import com.isitechproject.easycardwallet.LOYALTY_CARD_ID
import com.isitechproject.easycardwallet.LOYALTY_CARD_SCREEN
import com.isitechproject.easycardwallet.SCAN_LOYALTY_CARD_SCREEN
import com.isitechproject.easycardwallet.model.LoyaltyCard
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.LoyaltyCardService
import com.isitechproject.easycardwallet.model.service.SharedLoyaltyCardService
import com.isitechproject.easycardwallet.model.service.UserService
import com.isitechproject.easycardwallet.screens.EasyCardWalletAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class SharedLoyaltyCardsListViewModel @Inject constructor(
    accountService: AccountService,
    private val userService: UserService,
    private val loyaltyCardService: LoyaltyCardService,
    private val sharedLoyaltyCardService: SharedLoyaltyCardService,
): EasyCardWalletAppViewModel(accountService) {
    val sharedLoyaltyCards = sharedLoyaltyCardService.currentUserSharedLoyaltyCards
    private val loyaltyCards = loyaltyCardService.userSharedLoyaltyCards

    fun getLoyaltyCardName(id: String): String {
        lateinit var name: String

        launchCatching {
            name = loyaltyCards.first().first { card -> card.id == id }.name
        }

        return name
    }

    fun getUserEmail(id: String): String {
        lateinit var email: String

        launchCatching {
            email = userService.getOneById(id).email
        }

        return email
    }

    fun stopSharing(id: String) {
        launchCatching {
            sharedLoyaltyCardService.delete(id)
        }
    }
}