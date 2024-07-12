package com.isitechproject.easycardwallet.model.service.impl

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.isitechproject.easycardwallet.model.User
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.impl.exception.NotCreatedException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(): AccountService {
    private val firebaseAuth = Firebase.auth

    override val currentUser: Flow<User?>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.let { User(it.uid) })
                }
            firebaseAuth.addAuthStateListener(listener)
            awaitClose { firebaseAuth.removeAuthStateListener(listener) }
        }

    override val currentUserId: String
        get() = firebaseAuth.currentUser?.uid.orEmpty()

    override fun hasUser(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override suspend fun signIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun signUp(email: String, password: String): String {
        return firebaseAuth
            .createUserWithEmailAndPassword(email, password)
            .await()
            .user?.uid
            ?: throw NotCreatedException("Could not create account for user with email $email")
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }

    override suspend fun deleteAccount() {
        firebaseAuth.currentUser!!.delete().await()
    }
}