package com.isitechproject.easycardwallet.model.service.impl

import android.content.res.Resources.NotFoundException
import com.google.firebase.Firebase
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.isitechproject.easycardwallet.model.LoyaltyCard
import com.isitechproject.easycardwallet.model.SharedLoyaltyCard
import com.isitechproject.easycardwallet.model.service.LoyaltyCardService
import com.isitechproject.easycardwallet.model.service.SharedLoyaltyCardService
import com.isitechproject.easycardwallet.model.service.UserService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SharedLoyaltyCardServiceImpl @Inject constructor(
    private val userService: UserService
): SharedLoyaltyCardService {
    private val db = Firebase.firestore
    private val sharedLoyaltyCardsPath = db.collection(SHARED_LOYALTY_CARDS_COLLECTION)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val sharedLoyaltyCards: Flow<List<SharedLoyaltyCard>>
        get() =
            userService.currentUser.flatMapLatest { user ->
                sharedLoyaltyCardsPath
                    .whereEqualTo(UID_FIELD, user?.uid)
                    .dataObjects()
            }

    override suspend fun create(sharedLoyaltyCard: SharedLoyaltyCard) {
        sharedLoyaltyCardsPath.add(sharedLoyaltyCard).await()
    }

    override suspend fun getOne(id: String): SharedLoyaltyCard {
        val response = sharedLoyaltyCardsPath.document(id).get().await()

        return response.toObject<SharedLoyaltyCard>()
            ?.withId(response.id)
            ?: throw NotFoundException("Could not find loyalty card with id: $id")
    }

    override suspend fun update(sharedLoyaltyCard: SharedLoyaltyCard) {
        sharedLoyaltyCardsPath.document(sharedLoyaltyCard.id).set(sharedLoyaltyCard).await()
    }

    override suspend fun delete(id: String) {
        sharedLoyaltyCardsPath.document(id).delete().await()
    }
}