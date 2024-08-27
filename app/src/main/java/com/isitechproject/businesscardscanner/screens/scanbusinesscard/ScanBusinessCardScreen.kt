package com.isitechproject.businesscardscanner.screens.scanbusinesscard

import android.net.Uri
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
import com.google.mlkit.vision.barcode.common.Barcode
import com.isitechproject.barcodescanner.BARCODE_FORMAT
import com.isitechproject.barcodescanner.BARCODE_ARG_NAME
import com.isitechproject.barcodescanner.BARCODE_DEFAULT
import com.isitechproject.barcodescanner.CREATE_LOYALTY_CARD_SCREEN
import com.isitechproject.businesscardscanner.ANALYZED_TEXT_ARG
import com.isitechproject.businesscardscanner.BusinessCardScannerActivity
import com.isitechproject.businesscardscanner.CREATE_BUSINESS_CARD_SCREEN

@Composable
fun ScanBusinessCardScreen(
    activity: BusinessCardScannerActivity,
    openCreationScreen: (String) -> Unit,
    viewModel: ScanBusinessCardViewModel = hiltViewModel()
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
    activity: BusinessCardScannerActivity,
    openCreationScreen: (String) -> Unit,
    viewModel: ScanBusinessCardViewModel,
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
                        ) { text, picture ->
                            openCreationScreen(
                                "$CREATE_BUSINESS_CARD_SCREEN?$ANALYZED_TEXT_ARG=${Uri.encode(text.text)}&${Uri.encode(picture)}"
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