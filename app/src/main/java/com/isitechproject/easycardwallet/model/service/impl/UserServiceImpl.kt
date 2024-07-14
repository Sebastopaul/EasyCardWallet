package com.isitechproject.easycardwallet.model.service.impl

import com.google.firebase.Firebase
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.isitechproject.easycardwallet.model.User
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.UserService
import com.isitechproject.easycardwallet.model.service.impl.exception.NotAuthenticatedException
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserServiceImpl @Inject constructor(private val auth: AccountService): UserService {
    private val db = Firebase.firestore
    private val usersPath = db.collection(USERS_COLLECTION)

    override val currentUserId = auth.currentUserId
    override val currentUser = usersPath.where(Filter.equalTo("uid", auth.currentUserId))
        .dataObjects<User>()
        .map { it.first().withId<User>(auth.currentUserId) }

    override suspend fun create(user: User, password: String) {
        val registeredUser = User(
            uid = auth.signUp(user.email, password),
            firstname = user.firstname,
            lastname = user.lastname,
            email = user.email,
            profilePicture = user.profilePicture,
        )
        usersPath.add(registeredUser).await()
    }

    override suspend fun updateAuthUser(user: User) {
        if (!auth.hasUser() || user.uid != auth.currentUserId)
            throw NotAuthenticatedException()
        usersPath.document(auth.currentUserId).set(user).await()
    }

    override suspend fun deleteAuthUser() {
        if (!auth.hasUser())
            throw NotAuthenticatedException()
        usersPath.document(auth.currentUserId).delete().await()
    }
}