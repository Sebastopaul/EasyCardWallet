package com.isitechproject.easycardwallet.model.service.impl

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.isitechproject.easycardwallet.model.LoyaltyCard
import com.isitechproject.easycardwallet.model.service.LoyaltyCardService
import com.isitechproject.easycardwallet.model.service.UserDataService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoyaltyCardServiceImpl @Inject constructor(
    private val userService: UserDataService
): LoyaltyCardService {
    private val db = Firebase.firestore
    private val usersPath = db.collection(USERS_COLLECTION)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val loyaltyCards: Flow<List<LoyaltyCard>>
        get() =
            userService.currentUser.flatMapLatest { user ->
                flow { user?.loyaltyCards?.map { it } }
            }

    override suspend fun create(loyaltyCard: LoyaltyCard) {
        usersPath.document(userService.currentUserId)
            .collection(LOYALTY_CARDS_COLLECTION)
            .add(loyaltyCard).await()
    }

    override suspend fun getOne(id: String): LoyaltyCard? {
        return usersPath.document(userService.currentUserId)
            .collection(LOYALTY_CARDS_COLLECTION)
            .document(id).get().await().toObject()
    }

    override suspend fun update(loyaltyCard: LoyaltyCard) {
        usersPath.document(userService.currentUserId)
            .collection(LOYALTY_CARDS_COLLECTION)
            .document(loyaltyCard.id).set(loyaltyCard).await()
    }

    override suspend fun delete(id: String) {
        usersPath.document(userService.currentUserId)
            .collection(LOYALTY_CARDS_COLLECTION)
            .document(id).delete().await()
    }
}