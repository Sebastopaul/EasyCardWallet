package com.isitechproject.easycardwallet.ui.components

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import coil.compose.AsyncImage

@Composable
fun ImageSelector(
    registerImage: (Bitmap) -> Unit,
    uri: Uri? = null, //target url to preview
    onSetUri : (Uri) -> Unit = {}, // selected / taken uri
) {
    var selectImage by remember { mutableStateOf(false) }
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            it?.let {
                onSetUri(it)
            }
        }
    )

    if (selectImage){
        selectImage = false
        imagePicker.launch(
            PickVisualMediaRequest(
                ActivityResultContracts.PickVisualMedia.ImageOnly
            )
        )
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = { selectImage = true }) {
                Text(text = "Select a profile picture")
            }
        }

        //preview selfie
        uri?.let {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = it,
                    modifier = Modifier.size(
                        160.dp
                    ),
                    contentDescription = null,
                    onSuccess = { success ->
                        registerImage(success.result.drawable.toBitmap())
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}