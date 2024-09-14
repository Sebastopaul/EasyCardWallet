package com.isitechproject.easycardwallet.model.service

import com.isitechproject.easycardwallet.model.AbstractSharedCard
import kotlinx.coroutines.flow.Flow

interface SharedCardService {
    val currentUserSharedCards: Flow<List<AbstractSharedCard>>
    val sharedCards: Flow<List<AbstractSharedCard>>
    suspend fun create(sharedCard: AbstractSharedCard)
    suspend fun getOne(id: String): AbstractSharedCard
    suspend fun getOneBySharedId(cardId: String, userId: String): AbstractSharedCard
    suspend fun getAllBySharedId(id: String): List<AbstractSharedCard>
    suspend fun update(sharedCard: AbstractSharedCard)
    suspend fun delete(id: String)
}