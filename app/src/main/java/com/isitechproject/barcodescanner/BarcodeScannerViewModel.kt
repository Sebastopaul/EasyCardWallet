package com.isitechproject.barcodescanner

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import com.google.common.util.concurrent.ListenableFuture
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.screens.EasyCardWalletAppViewModel
import com.isitechproject.barcodescanner.utils.ImageAnalyzer
import java.util.concurrent.ExecutorService
import javax.inject.Inject

class BarcodeScannerViewModel @Inject constructor(
    private val accountService: AccountService,
): EasyCardWalletAppViewModel(accountService) {
    fun cameraListener(
        cameraExecutor: ExecutorService,
        cameraProviderFuture: ListenableFuture<ProcessCameraProvider>,
        previewView: PreviewView,
        activity: AppCompatActivity,
    ) {
        val cameraProvider = cameraProviderFuture.get()

        // Preview
        val preview = Preview.Builder()
            .build()
            .also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

        // Image analyzer
        val imageAnalyzer = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .also {
                it.setAnalyzer(
                    cameraExecutor,
                    ImageAnalyzer(activity) { barcode ->
                        Toast.makeText(
                            activity,
                            "Value: " + barcode.rawValue,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }

        // Select back camera as a default
        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        try {
            // Unbind use cases before rebinding
            cameraProvider.unbindAll()

            // Bind use cases to camera
            cameraProvider.bindToLifecycle(
                activity, cameraSelector, preview, imageAnalyzer
            )

        } catch (exc: Exception) {
            exc.printStackTrace()
        }
    }
}