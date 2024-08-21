package com.isitechproject.easycardwallet.model.service.impl

import android.content.res.Resources.NotFoundException
import com.google.firebase.Firebase
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.isitechproject.easycardwallet.model.AbstractSharedCard
import com.isitechproject.easycardwallet.model.SharedLoyaltyCard
import com.isitechproject.easycardwallet.model.service.SharedCardService
import com.isitechproject.easycardwallet.model.service.UserService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SharedLoyaltyCardService @Inject constructor(
    private val userService: UserService
): SharedCardService {
    private val db = Firebase.firestore
    private val sharedLoyaltyCardsPath = db.collection(SHARED_LOYALTY_CARDS_COLLECTION)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val currentUserSharedCards: Flow<List<AbstractSharedCard>>
        get() =
            userService.currentUser.flatMapLatest { user ->
                sharedLoyaltyCardsPath
                    .whereEqualTo(UID_FIELD, user?.uid)
                    .dataObjects()
            }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val sharedCards: Flow<List<AbstractSharedCard>>
        get() =
            userService.currentUser.flatMapLatest { user ->
                sharedLoyaltyCardsPath
                    .whereEqualTo(SHARED_TO_UID_FIELD, user?.uid)
                    .dataObjects()
            }

    override suspend fun create(sharedCard: AbstractSharedCard) {
        val response = sharedLoyaltyCardsPath.add(sharedCard).await()

        update(sharedCard.withId(response.id))
    }

    override suspend fun getOne(id: String): AbstractSharedCard {
        val response = sharedLoyaltyCardsPath.document(id).get().await()

        return response.toObject<SharedLoyaltyCard>()
            ?.withId(response.id)
            ?: throw NotFoundException("Could not find loyalty card with id: $id")
    }

    override suspend fun update(sharedCard: AbstractSharedCard) {
        sharedLoyaltyCardsPath.document(sharedCard.id).set(sharedCard).await()
    }

    override suspend fun delete(id: String) {
        sharedLoyaltyCardsPath.document(id).delete().await()
    }

    companion object {
        private const val SHARED_TO_UID_FIELD = "sharedUid"
    }
}