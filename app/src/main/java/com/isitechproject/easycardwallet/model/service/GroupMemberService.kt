package com.isitechproject.easycardwallet.model.service

import com.isitechproject.easycardwallet.model.GroupMember
import kotlinx.coroutines.flow.Flow

interface GroupMemberService {
    val groupMembers: Flow<List<GroupMember>>
    suspend fun create(groupId: String, groupMember: GroupMember)
    suspend fun getOne(groupId: String, groupMemberId: String): GroupMember?
    suspend fun update(groupId: String, groupMember: GroupMember)
    suspend fun delete(groupId: String, groupMemberId: String)
}