package com.isitechproject.easycardwallet.model.service.impl

import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.isitechproject.easycardwallet.model.SharedLoyaltyCard
import com.isitechproject.easycardwallet.model.service.GroupMemberService
import com.isitechproject.easycardwallet.model.service.SharedLoyaltyCardService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SharedLoyaltyCardServiceImpl @Inject constructor(
    private val groupMemberService: GroupMemberService
): SharedLoyaltyCardService {
    private val db = Firebase.firestore
    private val groupsPath = db.collection(GROUPS_COLLECTION)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val sharedLoyaltyCards: Flow<List<SharedLoyaltyCard>>
        get() =
            groupMemberService.groupMembers.flatMapLatest { groupMembers ->
                flow { groupMembers.map { it.sharedLoyaltyCards } }
            }

    override suspend fun create(groupId: String, groupMemberId: String, sharedLoyaltyCard: SharedLoyaltyCard) {
        getSharedLoyaltyPath(groupId, groupMemberId).add(sharedLoyaltyCard).await()
    }

    override suspend fun getOne(groupId: String, groupMemberId: String, sharedLoyaltyCardId: String): SharedLoyaltyCard? {
        return getSharedLoyaltyDocument(groupId, groupMemberId, sharedLoyaltyCardId).get().await().toObject()
    }

    override suspend fun update(groupId: String, groupMemberId: String, sharedLoyaltyCard: SharedLoyaltyCard) {
        getSharedLoyaltyDocument(groupId, groupMemberId, sharedLoyaltyCard.id).set(sharedLoyaltyCard).await()
    }

    override suspend fun delete(groupId: String, groupMemberId: String, sharedLoyaltyCardId: String) {
        getSharedLoyaltyDocument(groupId, groupMemberId, sharedLoyaltyCardId).delete().await()
    }

    private fun getSharedLoyaltyPath(groupId: String, groupMemberId: String): CollectionReference {
        return groupsPath.document(groupId).collection(GROUP_MEMBERS_COLLECTION)
            .document(groupMemberId).collection(SHARED_LOYALTY_CARDS_COLLECTION)
    }

    private fun getSharedLoyaltyDocument(groupId: String, groupMemberId: String, sharedLoyaltyCardId: String): DocumentReference {
        return getSharedLoyaltyPath(groupId, groupMemberId).document(sharedLoyaltyCardId)
    }
}