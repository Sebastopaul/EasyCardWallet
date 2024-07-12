package com.isitechproject.easycardwallet.screens.loyaltycards.loyaltycardslistscreen

import android.util.Log
import com.isitechproject.easycardwallet.LOYALTY_CARD_DEFAULT_ID
import com.isitechproject.easycardwallet.LOYALTY_CARD_ID
import com.isitechproject.easycardwallet.LOYALTY_CARD_SCREEN
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
class LoyaltyCardsListViewModel @Inject constructor(
    private val accountService: AccountService,
    private val loyaltyCardService: LoyaltyCardService
): EasyCardWalletAppViewModel(accountService) {
    fun onAddClick(openScreen: (String) -> Unit) {
        openScreen("$LOYALTY_CARD_SCREEN?$LOYALTY_CARD_ID=$LOYALTY_CARD_DEFAULT_ID")
    }

    // TODO: place this in a settings screen
    fun onDeleteAccountCLick() {
        launchCatching {
            accountService.deleteAccount()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getLoyaltyCards(): List<LoyaltyCard> {
        val list = mutableListOf<LoyaltyCard>()

        Log.d("Cards", loyaltyCardService.loyaltyCards.toString())
        launchCatching {
            for (loyaltyCard in loyaltyCardService.loyaltyCards.flatMapConcat { it.asFlow() }.toList()) {
                list.add(loyaltyCard)
            }
        }
        return list
    }
}