package com.isitechproject.easycardwallet.model.service.impl

import android.content.res.Resources.NotFoundException
import com.google.firebase.Firebase
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.isitechproject.easycardwallet.model.AbstractCard
import com.isitechproject.easycardwallet.model.LoyaltyCard
import com.isitechproject.easycardwallet.model.service.CardService
import com.isitechproject.easycardwallet.model.service.LoyaltyCardService
import com.isitechproject.easycardwallet.model.service.SharedCardService
import com.isitechproject.easycardwallet.model.service.SharedLoyaltyCardService
import com.isitechproject.easycardwallet.model.service.UserService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
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
    override val userCards: Flow<List<AbstractCard>>
        get() =
            userService.currentUser.flatMapLatest { user ->
                loyaltyCardsPath
                    .whereEqualTo(UID_FIELD, user?.uid)
                    .dataObjects<LoyaltyCard>()
            }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val userSharedCards: Flow<List<AbstractCard>>
        get() =
            sharedLoyaltyCardsService.currentUserSharedCards.flatMapLatest { sharedLoyaltyCardsList ->
                val ids = sharedLoyaltyCardsList.map { it.sharedCardId }
                if (ids.isNotEmpty()) {
                    loyaltyCardsPath
                        .whereIn(ID_FIELD, ids)
                        .dataObjects<LoyaltyCard>()
                } else {
                    flow { emptyList<LoyaltyCard>() }
                }
            }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val sharedCards: Flow<List<AbstractCard>>
        get() =
            sharedLoyaltyCardsService.sharedCards.flatMapLatest { sharedLoyaltyCardsList ->
                val ids = sharedLoyaltyCardsList.map { it.sharedCardId }
                if (ids.isNotEmpty()) {
                    loyaltyCardsPath
                        .whereIn(ID_FIELD, ids)
                        .dataObjects<LoyaltyCard>()
                } else {
                    flow { emptyList<AbstractCard>() }
                }
            }

    override suspend fun create(card: AbstractCard): String {
        val response = loyaltyCardsPath.add(card).await()

        update(card.withId(response.id))

        return response.id
    }

    override suspend fun getOne(id: String): AbstractCard {
        val response = loyaltyCardsPath.document(id).get().await()

        return response.toObject<LoyaltyCard>()
            ?.withId(response.id)
            ?: throw NotFoundException("Could not find loyalty card with id: $id")
    }

    override suspend fun update(card: AbstractCard) {
        loyaltyCardsPath.document(card.id).set(card).await()
    }

    override suspend fun delete(id: String) {
        loyaltyCardsPath.document(id).delete().await()
    }
}