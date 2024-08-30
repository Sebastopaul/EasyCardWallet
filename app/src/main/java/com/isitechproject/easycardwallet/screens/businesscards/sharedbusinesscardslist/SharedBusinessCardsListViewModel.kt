package com.isitechproject.easycardwallet.screens.businesscards.sharedbusinesscardslist

import com.isitechproject.easycardwallet.model.User
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.SharedBusinessCardService
import com.isitechproject.easycardwallet.model.service.UserService
import com.isitechproject.easycardwallet.model.service.BusinessCardService
import com.isitechproject.easycardwallet.screens.EasyCardWalletAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SharedBusinessCardsListViewModel @Inject constructor(
    accountService: AccountService,
    private val userService: UserService,
    businessCardService: BusinessCardService,
    private val sharedBusinessCardService: SharedBusinessCardService,
): EasyCardWalletAppViewModel(accountService) {
    val sharedBusinessCards = sharedBusinessCardService.currentUserSharedCards
    val businessCards = businessCardService.userCards
    private val users = mutableListOf<User>()

    fun initializeData() {
        runBlocking {
            sharedBusinessCards.first().forEach {
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
            sharedBusinessCardService.delete(id)
        }
    }
}