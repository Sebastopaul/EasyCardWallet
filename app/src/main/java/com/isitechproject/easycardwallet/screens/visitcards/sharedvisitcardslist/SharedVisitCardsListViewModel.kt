package com.isitechproject.easycardwallet.screens.visitcards.sharedvisitcardslist

import com.isitechproject.easycardwallet.model.User
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.SharedCardService
import com.isitechproject.easycardwallet.model.service.SharedVisitCardService
import com.isitechproject.easycardwallet.model.service.UserService
import com.isitechproject.easycardwallet.model.service.VisitCardService
import com.isitechproject.easycardwallet.screens.EasyCardWalletAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SharedVisitCardsListViewModel @Inject constructor(
    accountService: AccountService,
    private val userService: UserService,
    visitCardService: VisitCardService,
    private val sharedVisitCardService: SharedVisitCardService,
): EasyCardWalletAppViewModel(accountService) {
    val sharedVisitCards = sharedVisitCardService.currentUserSharedCards
    val visitCards = visitCardService.userCards
    private val users = mutableListOf<User>()

    fun initializeData() {
        runBlocking {
            sharedVisitCards.first().forEach {
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
            sharedVisitCardService.delete(id)
        }
    }
}