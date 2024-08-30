package com.isitechproject.easycardwallet.model.service.impl

import android.content.res.Resources.NotFoundException
import com.google.firebase.Firebase
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.isitechproject.easycardwallet.model.AbstractCard
import com.isitechproject.easycardwallet.model.BusinessCard
import com.isitechproject.easycardwallet.model.service.SharedBusinessCardService
import com.isitechproject.easycardwallet.model.service.UserService
import com.isitechproject.easycardwallet.model.service.BusinessCardService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BusinessCardServiceImpl @Inject constructor(
    private val userService: UserService,
    private val sharedBusinessCardsService: SharedBusinessCardService,
): BusinessCardService {
    private val db = Firebase.firestore
    private val businessCardsPath = db.collection(BUSINESS_CARDS_COLLECTION)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val userCards: Flow<List<AbstractCard>>
        get() =
            userService.currentUser.flatMapLatest { user ->
                businessCardsPath
                    .whereEqualTo(UID_FIELD, user?.uid)
                    .dataObjects<BusinessCard>()
            }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val userSharedCards: Flow<List<AbstractCard>>
        get() =
            sharedBusinessCardsService.currentUserSharedCards.flatMapLatest { sharedBusinessCardsList ->
                val ids = sharedBusinessCardsList.map { it.sharedCardId }
                if (ids.isNotEmpty()) {
                    businessCardsPath
                        .whereIn(ID_FIELD, ids)
                        .dataObjects<BusinessCard>()
                } else {
                    flow { emptyList<BusinessCard>() }
                }
            }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val sharedCards: Flow<List<AbstractCard>>
        get() =
            sharedBusinessCardsService.sharedCards.flatMapLatest { sharedBusinessCardsList ->
                val ids = sharedBusinessCardsList.map { it.sharedCardId }
                if (ids.isNotEmpty()) {
                    businessCardsPath
                        .whereIn(ID_FIELD, ids)
                        .dataObjects<BusinessCard>()
                } else {
                    flow { emptyList<AbstractCard>() }
                }
            }

    override suspend fun create(card: AbstractCard): String {
        val response = businessCardsPath.add(card).await()

        update(card.withId(response.id))

        return response.id
    }

    override suspend fun getOne(id: String): AbstractCard {
        val response = businessCardsPath.document(id).get().await()

        return response.toObject<BusinessCard>()
            ?.withId(response.id)
            ?: throw NotFoundException("Could not find business card with id: $id")
    }

    override suspend fun update(card: AbstractCard) {
        businessCardsPath.document(card.id).set(card).await()
    }

    override suspend fun delete(id: String) {
        businessCardsPath.document(id).delete().await()
    }
}