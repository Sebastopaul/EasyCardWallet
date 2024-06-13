package com.isitechproject.easycardwallet.model.service.impl

import com.google.firebase.Firebase
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.isitechproject.easycardwallet.model.User
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.UserDataService
import com.isitechproject.easycardwallet.model.service.impl.exception.NotAuthenticatedException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserDataServiceImpl @Inject constructor(private val auth: AccountService): UserDataService {
    private val db = Firebase.firestore
    private val usersPath = db.collection(USERS_COLLECTION)

    override val currentUser = usersPath.document(auth.currentUserId).dataObjects<User>()
    override val currentUserId = auth.currentUserId

    override suspend fun create(user: User) {
        usersPath.add(user).await()
    }

    override suspend fun getAuthUser(): User? {
        return if (auth.hasUser())
                usersPath.document(auth.currentUserId).get().await().toObject()
            else null
    }

    override suspend fun updateAuthUser(user: User) {
        if (!auth.hasUser() || user.id != auth.currentUserId)
            throw NotAuthenticatedException()
        usersPath.document(auth.currentUserId).set(user).await()
    }

    override suspend fun deleteAuthUser() {
        if (!auth.hasUser())
            throw NotAuthenticatedException()
        usersPath.document(auth.currentUserId).delete().await()
    }
}