package com.isitechproject.barcodescanner.screens.scanloyaltycard

import android.net.Uri
import android.util.Base64
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import com.isitechproject.barcodescanner.BARCODE_FORMAT
import com.isitechproject.barcodescanner.BASE64_BARCODE_ARG_NAME
import com.isitechproject.barcodescanner.BarcodeScannerActivity
import com.isitechproject.barcodescanner.CREATE_LOYALTY_CARD_SCREEN

@Composable
fun ScanLoyaltyCardScreen(
    activity: BarcodeScannerActivity,
    openCreationScreen: (String) -> Unit,
    viewModel: ScanLoyaltyCardViewModel = hiltViewModel()
) {

    Surface(color = MaterialTheme.colorScheme.background) {
        CameraPreview(
            activity,
            openCreationScreen,
            viewModel
        )
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

    cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview)
}

@Composable
fun CameraPreview(
    activity: BarcodeScannerActivity,
    openCreationScreen: (String) -> Unit,
    viewModel: ScanLoyaltyCardViewModel,
) {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(activity)

    AndroidView(
        factory = { context ->
            PreviewView(context).apply {
                layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                scaleType = PreviewView.ScaleType.FILL_START
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                post {
                    cameraProviderFuture.addListener({
                        viewModel.cameraListener(
                            activity.cameraExecutor,
                            cameraProviderFuture,
                            this,
                            activity,
                        ) { barcode ->
                            val base64Barcode = Base64.encodeToString(barcode.rawBytes, Base64.DEFAULT)

                            openCreationScreen("$CREATE_LOYALTY_CARD_SCREEN?$BASE64_BARCODE_ARG_NAME=${Uri.encode(base64Barcode)}&$BARCODE_FORMAT=${barcode.format}")
                        }
                    }, ContextCompat.getMainExecutor(context))
                }
            }
        }
    )
}