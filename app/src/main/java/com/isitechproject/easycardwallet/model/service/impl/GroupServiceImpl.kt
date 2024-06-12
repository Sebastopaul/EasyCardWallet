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
    @OptIn(ExperimentalCoroutinesApi::class)
    override val groups: Flow<List<Group>>
        get() = auth.currentUser.flatMapLatest { user ->
            Firebase.firestore.collection(GROUPS_COLLECTION).dataObjects<Group>().map {
                it.filter { group ->
                    group.members.any { member ->
                        member.userId == user?.id
                    }
                }
            }
        }

    override suspend fun create(group: Group) {
        val createdGroup = Firebase.firestore
            .collection(GROUPS_COLLECTION)
            .add(group).await()
        groupMemberService.create(
            GroupMember(
                createdGroup.id,
                auth.currentUserId,
                true,
            ),
        )
    }

    override suspend fun getOne(id: String): Group? {
        return Firebase.firestore
            .collection(GROUPS_COLLECTION)
            .document(id).get().await().toObject()
    }

    override suspend fun update(group: Group) {
        Firebase.firestore
            .collection(GROUPS_COLLECTION)
            .document(group.id).set(group).await()
    }

    override suspend fun delete(id: String) {
        Firebase.firestore
            .collection(GROUPS_COLLECTION)
            .document(id).delete().await()
    }

    companion object {
        private const val GROUPS_COLLECTION = "Groups"
    }
}