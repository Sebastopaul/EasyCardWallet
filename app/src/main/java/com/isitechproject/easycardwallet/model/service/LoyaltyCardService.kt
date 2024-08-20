package com.isitechproject.easycardwallet.model.service

import com.google.firebase.firestore.DocumentSnapshot
import com.isitechproject.easycardwallet.model.LoyaltyCard
import kotlinx.coroutines.flow.Flow

interface LoyaltyCardService {
    val userSharedLoyaltyCards: Flow<List<LoyaltyCard>>
    val userLoyaltyCards: Flow<List<LoyaltyCard>>
    val sharedLoyaltyCards: Flow<List<LoyaltyCard>>
    suspend fun create(loyaltyCard: LoyaltyCard)
    suspend fun getOne(id: String): LoyaltyCard?
    suspend fun update(loyaltyCard: LoyaltyCard)
    suspend fun delete(id: String)
}