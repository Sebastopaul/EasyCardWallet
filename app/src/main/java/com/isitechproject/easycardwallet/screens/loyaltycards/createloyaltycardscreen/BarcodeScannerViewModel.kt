package com.isitechproject.easycardwallet.screens.loyaltycards.createloyaltycardscreen

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import com.google.common.util.concurrent.ListenableFuture
import com.isitechproject.easycardwallet.databinding.BarcodeScannerBinding
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.screens.EasyCardWalletActivityViewModel
import com.isitechproject.easycardwallet.screens.EasyCardWalletAppViewModel
import com.isitechproject.easycardwallet.utils.BarcodeBoxView
import com.isitechproject.easycardwallet.utils.ImageAnalyzerWithBoxView
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

@HiltViewModel
class BarcodeScannerViewModel @Inject constructor(
    accountService: AccountService
): EasyCardWalletAppViewModel(accountService) {

    fun cameraListener(
        cameraExecutor: ExecutorService,
        cameraProviderFuture: ListenableFuture<ProcessCameraProvider>,
        binding: BarcodeScannerBinding,
        barcodeBoxView: BarcodeBoxView,
        activity: AppCompatActivity,
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
                    )
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