package com.isitechproject.easycardwallet.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap

@Composable
fun BitmapImage(
    bitmap: Bitmap,
    modifier: Modifier = Modifier,
    description: String = "",
) {
    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = description,
        modifier = modifier.fillMaxSize()
    )
}