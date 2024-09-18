package com.isitechproject.easycardwallet.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import com.isitechproject.easycardwallet.R

@Composable
fun BitmapImage(
    bitmap: Bitmap,
    modifier: Modifier = Modifier,
    description: String = stringResource(R.string.empty),
) {
    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = description,
        modifier = modifier.fillMaxSize()
    )
}