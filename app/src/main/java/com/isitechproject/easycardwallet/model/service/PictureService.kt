package com.isitechproject.easycardwallet.model.service

import com.isitechproject.easycardwallet.model.AbstractCard
import com.isitechproject.easycardwallet.model.BusinessCardPicture

interface PictureService {
    suspend fun upload(picture: BusinessCardPicture): String
    suspend fun getOne(id: String): BusinessCardPicture?
    suspend fun delete(id: String)
}