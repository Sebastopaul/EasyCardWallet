package com.isitechproject.easycardwallet.model.service.impl

import com.google.firebase.Firebase
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.isitechproject.easycardwallet.model.Group
import com.isitechproject.easycardwallet.model.GroupMember
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.GroupMemberService
import com.isitechproject.easycardwallet.model.service.GroupService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GroupServiceImpl @Inject constructor(
    private val auth: AccountService,
    private val groupMemberService: GroupMemberService,
): GroupService {
    private val db = Firebase.firestore
    private val groupsPath = db.collection(GROUPS_COLLECTION)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val groups: Flow<List<Group>>
        get() = auth.currentUser.flatMapLatest { user ->
            groupsPath.dataObjects<Group>().map { groupList ->
                groupList.filter { group ->
                    group.members.any { it.userId == user?.id }
                }
            }
        }

    override suspend fun create(group: Group) {
        val createdGroup = groupsPath.add(group).await()
        groupMemberService.create(
            createdGroup.id,
            GroupMember(
                createdGroup.id,
                auth.currentUserId,
                true,
            ),
        )
    }

    override suspend fun getOne(id: String): Group? {
        return groupsPath.document(id).get().await().toObject()
    }

    override suspend fun update(group: Group) {
        groupsPath.document(group.id).set(group).await()
    }

    override suspend fun delete(id: String) {
        groupsPath.document(id).delete().await()
    }
}