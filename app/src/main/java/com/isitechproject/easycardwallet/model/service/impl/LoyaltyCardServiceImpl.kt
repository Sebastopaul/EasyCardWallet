package com.isitechproject.easycardwallet.model.service.impl

import android.content.res.Resources.NotFoundException
import com.google.firebase.Firebase
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.isitechproject.easycardwallet.model.LoyaltyCard
import com.isitechproject.easycardwallet.model.service.LoyaltyCardService
import com.isitechproject.easycardwallet.model.service.SharedLoyaltyCardService
import com.isitechproject.easycardwallet.model.service.UserService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoyaltyCardServiceImpl @Inject constructor(
    private val userService: UserService,
    private val sharedLoyaltyCardsService: SharedLoyaltyCardService,
): LoyaltyCardService {
    private val db = Firebase.firestore
    private val loyaltyCardsPath = db.collection(LOYALTY_CARDS_COLLECTION)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val userLoyaltyCards: Flow<List<LoyaltyCard>>
        get() =
            userService.currentUser.flatMapLatest { user ->
                loyaltyCardsPath
                    .whereEqualTo(UID_FIELD, user?.uid)
                    .dataObjects()
            }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val sharedLoyaltyCards: Flow<List<LoyaltyCard>>
        get() =
            sharedLoyaltyCardsService.sharedLoyaltyCards.flatMapLatest { sharedLoyaltyCardsList ->
                val ids = sharedLoyaltyCardsList.map { it.loyaltyCardId }
                loyaltyCardsPath
                    .whereIn(ID_FIELD, ids)
                    .dataObjects()
            }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val loyaltyCards: Flow<List<LoyaltyCard>>
        get() =
            userLoyaltyCards.flatMapMerge {
                sharedLoyaltyCards
            }

    override suspend fun create(loyaltyCard: LoyaltyCard) {
        val response = loyaltyCardsPath.add(loyaltyCard).await()

        update(loyaltyCard.withId(response.id))
    }

    override suspend fun getOne(id: String): LoyaltyCard {
        val response = loyaltyCardsPath.document(id).get().await()

        return response.toObject<LoyaltyCard>()
            ?.withId(response.id)
            ?: throw NotFoundException("Could not find loyalty card with id: $id")
    }

    override suspend fun update(loyaltyCard: LoyaltyCard) {
        loyaltyCardsPath.document(loyaltyCard.id).set(loyaltyCard).await()
    }

    override suspend fun delete(id: String) {
        loyaltyCardsPath.document(id).delete().await()
    }
}