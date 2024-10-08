package com.isitechproject.barcodescanner.screens.scanloyaltycard

import android.net.Uri
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.mlkit.vision.barcode.common.Barcode
import com.isitechproject.barcodescanner.BARCODE_ARG_NAME
import com.isitechproject.barcodescanner.BARCODE_DEFAULT
import com.isitechproject.barcodescanner.BARCODE_FORMAT
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
                            openCreationScreen(
                                "$CREATE_LOYALTY_CARD_SCREEN?$BARCODE_ARG_NAME=${Uri.encode(getBarcodeValue(barcode))}&$BARCODE_FORMAT=${barcode.format}"
                            )
                        }
                    }, ContextCompat.getMainExecutor(context))
                }
            }
        }
    )
}

fun getBarcodeValue(barcode: Barcode): String {
    return when (barcode.valueType) {
        Barcode.TYPE_URL -> barcode.url.toString()
        Barcode.TYPE_EMAIL -> barcode.email.toString()
        Barcode.TYPE_TEXT -> barcode.displayValue.toString()
        Barcode.TYPE_PHONE -> barcode.phone.toString()
        else -> barcode.rawValue ?: BARCODE_DEFAULT
    }
}