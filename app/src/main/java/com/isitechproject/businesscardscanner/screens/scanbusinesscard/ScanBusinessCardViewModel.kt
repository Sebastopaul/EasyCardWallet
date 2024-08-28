package com.isitechproject.businesscardscanner.screens.scanbusinesscard

import android.util.Log
import android.util.Size
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.core.resolutionselector.ResolutionStrategy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import com.google.common.util.concurrent.ListenableFuture
import com.isitechproject.businesscardscanner.utils.ImageAnalyzerForBusinessCards
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.screens.EasyCardWalletAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.ExecutorService
import javax.inject.Inject

@HiltViewModel
class ScanBusinessCardViewModel @Inject constructor(
    accountService: AccountService,
): EasyCardWalletAppViewModel(accountService) {
    fun cameraListener(
        cameraExecutor: ExecutorService,
        cameraProviderFuture: ListenableFuture<ProcessCameraProvider>,
        previewView: PreviewView,
        activity: AppCompatActivity,
    ) {
        val cameraProvider = cameraProviderFuture.get()

        val resolutionSelector = ResolutionSelector.Builder()
            .setResolutionStrategy(ResolutionStrategy(
                Size(previewView.width, previewView.height),
                ResolutionStrategy.FALLBACK_RULE_CLOSEST_LOWER
            ))
            .build()

        // Preview
        val preview = Preview.Builder()
            .setResolutionSelector(resolutionSelector)
            .build()
            .apply {
                setSurfaceProvider(previewView.surfaceProvider)
            }

        // Image analyzer
        val imageAnalyzer = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .also {
                it.setAnalyzer(
                    cameraExecutor,
                    ImageAnalyzerForBusinessCards(
                        activity,
                    ) { text, picture ->
                        Log.d("TEST", "PASS_HANDLER")
                        Log.d("TEST", text.text)
                        Toast.makeText(activity, text.text, Toast.LENGTH_LONG).show()
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
                activity,
                cameraSelector,
                preview,
                imageAnalyzer,
            )

        } catch (exc: Exception) {
            exc.printStackTrace()
        }
    }
}