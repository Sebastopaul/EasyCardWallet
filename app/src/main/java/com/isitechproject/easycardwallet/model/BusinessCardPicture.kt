package com.isitechproject.easycardwallet.model

import android.net.Uri

data class BusinessCardPicture(
    val picture: String = "",
    val name: String = "",
    val uri: Uri = Uri.EMPTY,
    val uploadUri: Uri? = Uri.EMPTY,
    val uid: String = "",
) : Model()