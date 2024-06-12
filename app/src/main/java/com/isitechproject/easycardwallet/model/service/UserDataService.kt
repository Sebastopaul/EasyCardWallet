package com.isitechproject.easycardwallet.model.service

import com.isitechproject.easycardwallet.model.User

interface UserDataService {
    suspend fun create(user: User)
    suspend fun getAuthUser(): User?
    suspend fun updateAuthUser(user: User)
    suspend fun deleteAuthUser()
}