package com.isitechproject.easycardwallet.model.service

import com.isitechproject.easycardwallet.model.Picture
import kotlinx.coroutines.flow.Flow

interface PictureService {
    suspend fun create(picture: Picture)
    suspend fun getOne(id: String): Picture?
    suspend fun update(picture: Picture)
    suspend fun delete(id: String)
}