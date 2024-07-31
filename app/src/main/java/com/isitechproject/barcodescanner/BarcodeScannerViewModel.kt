package com.isitechproject.barcodescanner

import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.common.Barcode
import com.isitechproject.easycardwallet.databinding.BarcodeScannerBinding
import com.isitechproject.easycardwallet.model.LoyaltyCard
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.LoyaltyCardService
import com.isitechproject.easycardwallet.screens.EasyCardWalletAppViewModel
import com.isitechproject.easycardwallet.utils.BarcodeBoxView
import com.isitechproject.easycardwallet.utils.ImageAnalyzerWithBoxView
import java.util.concurrent.ExecutorService
import javax.inject.Inject

class BarcodeScannerViewModel @Inject constructor(
    private val accountService: AccountService,
): EasyCardWalletAppViewModel(accountService) {
    private fun openCreateCardScreen(createCard: () -> Unit) {
        createCard()
    }

    fun cameraListener(
        cameraExecutor: ExecutorService,
        cameraProviderFuture: ListenableFuture<ProcessCameraProvider>,
        binding: BarcodeScannerBinding,
        barcodeBoxView: BarcodeBoxView,
        activity: AppCompatActivity,
        createCard: () -> Unit
    ) {
        val cameraProvider = cameraProviderFuture.get()

        // Preview
        val preview = Preview.Builder()
            .build()
            .also {
                it.setSurfaceProvider(binding.previewView.surfaceProvider)
            }

        // Image analyzer
        val imageAnalyzer = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .also {
                it.setAnalyzer(
                    cameraExecutor,
                    ImageAnalyzerWithBoxView(
                        activity,
                        barcodeBoxView,
                        binding.previewView.width.toFloat(),
                        binding.previewView.height.toFloat(),
                    ) { barcode ->
                        openCreateCardScreen(createCard)
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