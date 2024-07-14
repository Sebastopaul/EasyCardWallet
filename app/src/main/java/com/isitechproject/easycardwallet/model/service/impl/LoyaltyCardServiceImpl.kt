package com.isitechproject.easycardwallet.model.service.impl

import android.content.res.Resources.NotFoundException
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.isitechproject.easycardwallet.model.LoyaltyCard
import com.isitechproject.easycardwallet.model.service.LoyaltyCardService
import com.isitechproject.easycardwallet.model.service.UserService
import com.isitechproject.easycardwallet.model.service.impl.exception.NotCreatedException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoyaltyCardServiceImpl @Inject constructor(
    private val userService: UserService
): LoyaltyCardService {
    private val db = Firebase.firestore
    private val loyaltyCardsPath = db.collection(LOYALTY_CARDS_COLLECTION)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val loyaltyCards: Flow<List<LoyaltyCard>>
        get() =
            userService.currentUser.flatMapLatest { user ->
                Firebase.firestore
                    .collection(LOYALTY_CARDS_COLLECTION)
                    .whereEqualTo(UID_FIELD, user?.uid)
                    .dataObjects()
            }

    override suspend fun create(loyaltyCard: LoyaltyCard) {
        loyaltyCardsPath.add(loyaltyCard).await()
    }

    override suspend fun getOne(id: String): LoyaltyCard {
        val response = loyaltyCardsPath.document(id).get().await()

        return response.toObject<LoyaltyCard>()
            ?.withId<LoyaltyCard>(response.id)
            ?: throw NotFoundException("Could not find loyalty card with id: $id")
    }

    override suspend fun update(loyaltyCard: LoyaltyCard) {
        loyaltyCardsPath.document(loyaltyCard.id).set(loyaltyCard).await()
    }

    override suspend fun delete(id: String) {
        loyaltyCardsPath.document(id).delete().await()
    }
}