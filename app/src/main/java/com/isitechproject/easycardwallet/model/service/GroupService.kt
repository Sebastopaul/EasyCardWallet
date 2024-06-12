package com.isitechproject.easycardwallet.model.service

import com.isitechproject.easycardwallet.model.Group
import kotlinx.coroutines.flow.Flow

interface GroupService {
    val groups: Flow<List<Group>>
    suspend fun create(group: Group)
    suspend fun getOne(id: String): Group?
    suspend fun update(group: Group)
    suspend fun delete(id: String)
}