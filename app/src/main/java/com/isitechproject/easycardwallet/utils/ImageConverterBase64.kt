package com.isitechproject.easycardwallet.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.res.painterResource
import java.io.ByteArrayOutputStream

class ImageConverterBase64 {

    companion object {
        fun to(): String? {
            val bitMap = BitmapFactory.decodeFile("/path/to/image.jpg")
            val byteArrayOutputStream = ByteArrayOutputStream()

            bitMap.compress(
                Bitmap.CompressFormat.JPEG,
                100,
                byteArrayOutputStream
            ) // bm is the bitmap object

            val byteArray = byteArrayOutputStream.toByteArray()

            return Base64.encodeToString(byteArray, Base64.DEFAULT)
        }

        fun from(base64String: String): Bitmap {
            val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            if (bitmap != null)
                return bitmap
            return Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8)
        }
    }
}