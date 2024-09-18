package com.isitechproject.businesscardscanner.screens.scanbusinesscard

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.isitechproject.businesscardscanner.BusinessCardScannerActivity

@Composable
fun ScanBusinessCardScreen(
    openCreationScreen: (String) -> Unit,
    viewModel: ScanBusinessCardViewModel = hiltViewModel()
) {
    val context = LocalContext.current as BusinessCardScannerActivity
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

    Surface(color = MaterialTheme.colorScheme.background) {
        AndroidView(
            factory = { previewContext ->
                PreviewView(previewContext).apply {
                    layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                    scaleType = PreviewView.ScaleType.FILL_START
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE

                    post {
                        cameraProviderFuture.addListener(
                            {
                                viewModel.cameraListener(
                                    context.cameraExecutor,
                                    cameraProviderFuture,
                                    this,
                                    context,
                                ) { text -> openCreationScreen(viewModel.buildUri(viewModel.parseText(text))) }
                            },
                            ContextCompat.getMainExecutor(previewContext)
                        )
                    }
                }
            }
        )
    }
}