package com.isitechproject.easycardwallet.model.service

import com.isitechproject.easycardwallet.model.SharedLoyaltyCard
import kotlinx.coroutines.flow.Flow

interface SharedLoyaltyCardService {
    val sharedLoyaltyCards: Flow<List<SharedLoyaltyCard>>
    suspend fun create(sharedLoyaltyCard: SharedLoyaltyCard)
    suspend fun getOne(id: String): SharedLoyaltyCard?
    suspend fun update(sharedLoyaltyCard: SharedLoyaltyCard)
    suspend fun delete(id: String)
}