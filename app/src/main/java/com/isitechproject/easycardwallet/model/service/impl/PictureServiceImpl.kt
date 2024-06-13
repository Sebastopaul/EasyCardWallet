package com.isitechproject.easycardwallet.model.service.impl

import com.google.firebase.Firebase
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.isitechproject.easycardwallet.model.Picture
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.PictureService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PictureServiceImpl @Inject constructor(
    private val auth: AccountService,
): PictureService {
    private val db = Firebase.firestore
    private val picturesPath = db.collection(PICTURES_COLLECTION)

    override suspend fun create(picture: Picture) {
        picturesPath.add(picture).await()
    }

    override suspend fun getOne(id: String): Picture? {
        return picturesPath.document(id).get().await().toObject()
    }

    override suspend fun update(picture: Picture) {
        picturesPath.document(picture.id).set(picture).await()
    }

    override suspend fun delete(id: String) {
        picturesPath.document(id).delete().await()
    }
}