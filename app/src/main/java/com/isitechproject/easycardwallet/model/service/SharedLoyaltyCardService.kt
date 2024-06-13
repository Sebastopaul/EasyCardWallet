package com.isitechproject.easycardwallet.model.service

import com.isitechproject.easycardwallet.model.LoyaltyCard
import com.isitechproject.easycardwallet.model.SharedLoyaltyCard
import kotlinx.coroutines.flow.Flow

interface SharedLoyaltyCardService {
    val sharedLoyaltyCards: Flow<List<SharedLoyaltyCard>>
    suspend fun create(groupId: String, groupMemberId: String, sharedLoyaltyCard: SharedLoyaltyCard)
    suspend fun getOne(groupId: String, groupMemberId: String, sharedLoyaltyCardId: String): SharedLoyaltyCard?
    suspend fun update(groupId: String, groupMemberId: String, sharedLoyaltyCard: SharedLoyaltyCard)
    suspend fun delete(groupId: String, groupMemberId: String, sharedLoyaltyCardId: String)
}