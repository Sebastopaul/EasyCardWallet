package com.isitechproject.easycardwallet.model.service.impl

import android.content.res.Resources.NotFoundException
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.isitechproject.easycardwallet.model.AbstractSharedCard
import com.isitechproject.easycardwallet.model.SharedBusinessCard
import com.isitechproject.easycardwallet.model.service.SharedBusinessCardService
import com.isitechproject.easycardwallet.model.service.UserService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SharedBusinessCardServiceImpl @Inject constructor(
    private val userService: UserService
): SharedBusinessCardService {
    private val db = Firebase.firestore
    private val sharedBusinessCardsPath = db.collection(SHARED_BUSINESS_CARDS_COLLECTION)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val currentUserSharedCards: Flow<List<AbstractSharedCard>>
        get() =
            userService.currentUser.flatMapLatest { user ->
                sharedBusinessCardsPath
                    .whereEqualTo(UID_FIELD, user?.uid)
                    .dataObjects<SharedBusinessCard>()
            }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val sharedCards: Flow<List<AbstractSharedCard>>
        get() =
            userService.currentUser.flatMapLatest { user ->
                sharedBusinessCardsPath
                    .whereEqualTo(SHARED_TO_UID_FIELD, user?.uid)
                    .dataObjects<SharedBusinessCard>()
            }

    override suspend fun create(sharedCard: AbstractSharedCard) {
        val response = sharedBusinessCardsPath.add(sharedCard).await()

        update(sharedCard.withId(response.id))
    }

    override suspend fun getOne(id: String): AbstractSharedCard {
        val response = sharedBusinessCardsPath.document(id).get().await()

        return response.toObject<SharedBusinessCard>()
            ?.withId(response.id)
            ?: throw NotFoundException("Could not find shared card with id: $id")
    }

    override suspend fun getOneBySharedId(id: String): AbstractSharedCard {

        Log.d("TEST", id);
        val response = sharedBusinessCardsPath
            .whereEqualTo(SHARED_CARD_ID_FIELD, id)
            .get().await()

        if (!response.isEmpty) {
            val sharedBusinessCard = response.first()
            Log.d("TEST_IF", sharedBusinessCard.id);

            return sharedBusinessCard
                .toObject<SharedBusinessCard>()
                .withId(sharedBusinessCard.id)
        }
        throw NotFoundException("Could not find shared card with id: $id")
    }

    override suspend fun update(sharedCard: AbstractSharedCard) {
        sharedBusinessCardsPath.document(sharedCard.id).set(sharedCard).await()
    }

    override suspend fun delete(id: String) {
        sharedBusinessCardsPath.document(id).delete().await()
    }

    companion object {
        private const val SHARED_TO_UID_FIELD = "sharedUid"
        private const val SHARED_CARD_ID_FIELD = "sharedCardId"
    }
}