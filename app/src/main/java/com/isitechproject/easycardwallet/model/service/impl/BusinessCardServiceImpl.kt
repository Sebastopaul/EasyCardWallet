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
    private val sharedVisitCardsService: SharedBusinessCardService,
): BusinessCardService {
    private val db = Firebase.firestore
    private val visitCardsPath = db.collection(VISIT_CARDS_COLLECTION)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val userCards: Flow<List<AbstractCard>>
        get() =
            userService.currentUser.flatMapLatest { user ->
                visitCardsPath
                    .whereEqualTo(UID_FIELD, user?.uid)
                    .dataObjects<BusinessCard>()
            }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val userSharedCards: Flow<List<AbstractCard>>
        get() =
            sharedVisitCardsService.currentUserSharedCards.flatMapLatest { sharedVisitCardsList ->
                val ids = sharedVisitCardsList.map { it.sharedCardId }
                if (ids.isNotEmpty()) {
                    visitCardsPath
                        .whereIn(ID_FIELD, ids)
                        .dataObjects<BusinessCard>()
                } else {
                    flow { emptyList<BusinessCard>() }
                }
            }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val sharedCards: Flow<List<AbstractCard>>
        get() =
            sharedVisitCardsService.sharedCards.flatMapLatest { sharedVisitCardsList ->
                val ids = sharedVisitCardsList.map { it.sharedCardId }
                if (ids.isNotEmpty()) {
                    visitCardsPath
                        .whereIn(ID_FIELD, ids)
                        .dataObjects<BusinessCard>()
                } else {
                    flow { emptyList<AbstractCard>() }
                }
            }

    override suspend fun create(card: AbstractCard) {
        val response = visitCardsPath.add(card).await()

        update(card.withId(response.id))
    }

    override suspend fun getOne(id: String): AbstractCard {
        val response = visitCardsPath.document(id).get().await()

        return response.toObject<BusinessCard>()
            ?.withId(response.id)
            ?: throw NotFoundException("Could not find visit card with id: $id")
    }

    override suspend fun update(card: AbstractCard) {
        visitCardsPath.document(card.id).set(card).await()
    }

    override suspend fun delete(id: String) {
        visitCardsPath.document(id).delete().await()
    }
}