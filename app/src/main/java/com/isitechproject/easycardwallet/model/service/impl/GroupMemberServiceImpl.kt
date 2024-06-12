package com.isitechproject.easycardwallet.model.service.impl

import com.google.firebase.Firebase
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.isitechproject.easycardwallet.model.GroupMember
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.GroupMemberService
import com.isitechproject.easycardwallet.model.service.GroupService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GroupMemberServiceImpl @Inject constructor(
    private val auth: AccountService,
): GroupMemberService {
    @OptIn(ExperimentalCoroutinesApi::class)
    override val groupMembers: Flow<List<GroupMember>>
        get() =
            auth.currentUser.flatMapLatest { user ->
                Firebase.firestore
                    .collection(GROUP_MEMBERS_COLLECTION)
                    .whereEqualTo(USER_ID_FIELD, user?.id)
                    .dataObjects()
            }

    override suspend fun create(groupMember: GroupMember) {
        val groupWithUserId = groupMember.copy(userId = auth.currentUserId)
        Firebase.firestore
            .collection(GROUP_MEMBERS_COLLECTION)
            .add(groupWithUserId).await()
    }

    override suspend fun getOne(id: String): GroupMember? {
        return Firebase.firestore
            .collection(GROUP_MEMBERS_COLLECTION)
            .document(id).get().await().toObject()
    }

    override suspend fun update(groupMember: GroupMember) {
        Firebase.firestore
            .collection(GROUP_MEMBERS_COLLECTION)
            .document(groupMember.id).set(groupMember).await()
    }

    override suspend fun delete(id: String) {
        Firebase.firestore
            .collection(GROUP_MEMBERS_COLLECTION)
            .document(id).delete().await()
    }

    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val GROUP_MEMBERS_COLLECTION = "GroupMembers"
    }
}