package com.isitechproject.easycardwallet.model.service

import com.isitechproject.easycardwallet.model.GroupMember
import kotlinx.coroutines.flow.Flow

interface GroupMemberService {
    val groupMembers: Flow<List<GroupMember>>
    suspend fun create(groupMember: GroupMember)
    suspend fun getOne(id: String): GroupMember?
    suspend fun update(groupMember: GroupMember)
    suspend fun delete(id: String)
}