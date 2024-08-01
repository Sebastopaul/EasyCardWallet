package com.isitechproject.barcodescanner.screens.scanloyaltycard

import android.Manifest
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import com.isitechproject.barcodescanner.BarcodeScannerActivity

@Composable
fun ScanLoyaltyCardScreen(
    activity: BarcodeScannerActivity,
    viewModel: ScanLoyaltyCardViewModel = hiltViewModel()
) {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(activity)
    Surface(color = MaterialTheme.colorScheme.background) {
        var isPermissionGranted by remember {
            mutableStateOf<Boolean?>(null)
        }
        val launcher =
            activity.registerForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
                isPermissionGranted = isGranted
            }
        when (isPermissionGranted) {
            true -> CameraPreview(cameraProviderFuture)
            false -> Text("permission pls")
            null -> Button(onClick = { launcher.launch(Manifest.permission.CAMERA) }) {
                Text(text = "Start!")
            }
        }
    }
}

fun bindPreview(
    cameraProvider: ProcessCameraProvider,
    lifecycleOwner: LifecycleOwner,
    previewView: PreviewView,
) {
    val preview: Preview = Preview.Builder()
        .build()

    val cameraSelector: CameraSelector = CameraSelector.Builder()
        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
        .build()

    preview.setSurfaceProvider(previewView.surfaceProvider)

    var camera = cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview)
}

@Composable
fun CameraPreview(cameraProviderFuture: ListenableFuture<ProcessCameraProvider>, ) {

    val lifecycleOwner = LocalLifecycleOwner.current
    AndroidView(
        factory = { context ->
            PreviewView(context).apply {
                layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, MATCH_PARENT)
                scaleType = PreviewView.ScaleType.FILL_START
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                post {
                    cameraProviderFuture.addListener(Runnable {
                        val cameraProvider = cameraProviderFuture.get()
                        bindPreview(
                            cameraProvider,
                            lifecycleOwner,
                            this,
                        )
                    }, ContextCompat.getMainExecutor(context))
                }
            }
        }
    )
}