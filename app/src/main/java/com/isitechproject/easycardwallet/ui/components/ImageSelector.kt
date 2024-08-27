package com.isitechproject.easycardwallet.ui.components

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import com.isitechproject.easycardwallet.R

@SuppressLint("InflateParams")
@Composable
fun ImageSelector(
    registerImage: (Bitmap) -> Unit,
    uri: Uri? = null, //target url to preview
    onSetUri : (Uri) -> Unit = {}, // selected / taken uri
) {
    val context = LocalContext.current as Activity
    var selectImage by remember { mutableStateOf(false) }
    var bitmap: Bitmap? by remember { mutableStateOf(null) }
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            it?.let {
                onSetUri(it)
            }
        }
    )

    if (selectImage) {
        selectImage = false
        imagePicker.launch(
            PickVisualMediaRequest(
                ActivityResultContracts.PickVisualMedia.ImageOnly
            )
        )
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        if (uri != null && bitmap == null) {
            val cropImageView = CropImageView(context)

            cropImageView.setImageCropOptions(
                CropImageOptions(
                    imageSourceIncludeCamera = false,
                    imageSourceIncludeGallery = true,
                    guidelines = CropImageView.Guidelines.ON,
                    cropMenuCropButtonTitle = "Add",
                )
            )
            cropImageView.setImageUriAsync(uri)
            cropImageView.setOnCropImageCompleteListener { _, result ->
                bitmap = result.bitmap
                bitmap?.let { registerImage(it) }
            }

            SideEffect {
                context.setContentView(cropImageView)
            }
        } else if (bitmap != null) {
            Box {
                Image(bitmap = bitmap!!.asImageBitmap(), contentDescription = "Selected image")
            }
        }
        
        Spacer(modifier = Modifier.padding(12.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = { selectImage = true },
                colors = ButtonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.Cyan,
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = Color.Transparent,
                )
            ) {
                Text(text = "Select a profile picture")
            }
        }
    }
}