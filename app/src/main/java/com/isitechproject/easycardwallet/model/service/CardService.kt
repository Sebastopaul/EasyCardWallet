package com.isitechproject.easycardwallet.model.service

import com.isitechproject.easycardwallet.model.AbstractCard
import kotlinx.coroutines.flow.Flow

interface CardService {
    val userSharedCards: Flow<List<AbstractCard>>
    val userCards: Flow<List<AbstractCard>>
    val sharedCards: Flow<List<AbstractCard>>
    suspend fun create(card: AbstractCard)
    suspend fun getOne(id: String): AbstractCard?
    suspend fun update(card: AbstractCard)
    suspend fun delete(id: String)
}