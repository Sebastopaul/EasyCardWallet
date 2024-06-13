package com.isitechproject.easycardwallet.model.service.impl

import com.google.firebase.Firebase
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.isitechproject.easycardwallet.model.Group
import com.isitechproject.easycardwallet.model.GroupMember
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.GroupMemberService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GroupMemberServiceImpl @Inject constructor(
    private val auth: AccountService,
): GroupMemberService {
    private val db = Firebase.firestore
    private val groupsPath = db.collection(GROUPS_COLLECTION)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val groupMembers: Flow<List<GroupMember>>
        get() = auth.currentUser.flatMapLatest { user ->
            groupsPath.dataObjects<Group>().map { groups ->
                groups.map { group ->
                    group.members.filter { it.userId == user?.id }
                }
            }
        }.flatMapLatest { flow { it.flatten() } }

    override suspend fun create(groupId: String, groupMember: GroupMember) {
        val groupMemberWithUserId = groupMember.copy(userId = auth.currentUserId)
        val groupMemberPath = groupsPath.document(groupId)
            .collection(GROUP_MEMBERS_COLLECTION)
        groupMemberPath.add(groupMemberWithUserId).await()
    }

    override suspend fun getOne(groupId: String, groupMemberId: String): GroupMember? {
        return groupsPath.document(groupId)
            .collection(GROUP_MEMBERS_COLLECTION)
            .document(groupMemberId).get().await().toObject()
    }

    override suspend fun update(groupId: String, groupMember: GroupMember) {
        groupsPath.document(groupId)
            .collection(GROUP_MEMBERS_COLLECTION)
            .document(groupMember.id).set(groupMember).await()
    }

    override suspend fun delete(groupId: String, groupMemberId: String) {
        groupsPath.document(groupId)
            .collection(GROUP_MEMBERS_COLLECTION)
            .document(groupMemberId).delete().await()
    }
}