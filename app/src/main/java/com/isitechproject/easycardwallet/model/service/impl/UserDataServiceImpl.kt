package com.isitechproject.easycardwallet.model.service.impl

import android.app.AuthenticationRequiredException
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.isitechproject.easycardwallet.model.User
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.UserDataService
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserDataServiceImpl @Inject constructor(private val auth: AccountService): UserDataService {
    override suspend fun create(user: User) {
        Firebase.firestore
            .collection(USERS_COLLECTION)
            .add(user).await()
    }

    override suspend fun getAuthUser(): User? {
        return if (auth.hasUser())
                Firebase.firestore
                .collection(USERS_COLLECTION)
                .document(auth.currentUserId).get().await().toObject()
            else null
    }

    override suspend fun updateAuthUser(user: User) {
        if (!auth.hasUser())
            throw Exception("Cannot update unauthenticated user.")
        Firebase.firestore
            .collection(USERS_COLLECTION)
            .document(auth.currentUserId).set(user).await()
    }

    override suspend fun deleteAuthUser() {
        if (!auth.hasUser())
            throw Exception("Cannot delete unauthenticated user.")
        Firebase.firestore
            .collection(USERS_COLLECTION)
            .document(auth.currentUserId).delete().await()
    }

    companion object {
        private const val USERS_COLLECTION = "Users"
    }
}