package com.isitechproject.easycardwallet.model.service.impl

import android.content.res.Resources.NotFoundException
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.isitechproject.easycardwallet.model.AbstractSharedCard
import com.isitechproject.easycardwallet.model.SharedLoyaltyCard
import com.isitechproject.easycardwallet.model.service.SharedCardService
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
    override val currentUserSharedCards: Flow<List<AbstractSharedCard>>
        get() =
            userService.currentUser.flatMapLatest { user ->
                sharedLoyaltyCardsPath
                    .whereEqualTo(UID_FIELD, user?.uid)
                    .dataObjects<SharedLoyaltyCard>()
            }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val sharedCards: Flow<List<AbstractSharedCard>>
        get() =
            userService.currentUser.flatMapLatest { user ->
                sharedLoyaltyCardsPath
                    .whereEqualTo(SHARED_TO_UID_FIELD, user?.uid)
                    .dataObjects<SharedLoyaltyCard>()
            }

    override suspend fun create(sharedCard: AbstractSharedCard) {
        val response = sharedLoyaltyCardsPath.add(sharedCard).await()

        update(sharedCard.withId(response.id))
    }

    override suspend fun getOne(id: String): AbstractSharedCard {
        val response = sharedLoyaltyCardsPath.document(id).get().await()

        return response.toObject<SharedLoyaltyCard>()
            ?.withId(response.id)
            ?: throw NotFoundException("Could not find shared card with id: $id")
    }

    override suspend fun getOneBySharedId(id: String): AbstractSharedCard {

        Log.d("TEST", id);
        val response = sharedLoyaltyCardsPath
            .whereEqualTo(SHARED_CARD_ID_FIELD, id)
            .get().await()

        if (!response.isEmpty) {
            val sharedLoyaltyCard = response.first()
            Log.d("TEST_IF", sharedLoyaltyCard.id);

            return sharedLoyaltyCard
                .toObject<SharedLoyaltyCard>()
                .withId(sharedLoyaltyCard.id)
        }
        throw NotFoundException("Could not find shared card with id: $id")
    }

    override suspend fun update(sharedCard: AbstractSharedCard) {
        sharedLoyaltyCardsPath.document(sharedCard.id).set(sharedCard).await()
    }

    override suspend fun delete(id: String) {
        sharedLoyaltyCardsPath.document(id).delete().await()
    }

    companion object {
        private const val SHARED_TO_UID_FIELD = "sharedUid"
        private const val SHARED_CARD_ID_FIELD = "sharedCardId"
    }
}