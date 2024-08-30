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
import androidx.compose.runtime.mutableStateOf
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.text.Text
import com.isitechproject.businesscardscanner.BusinessCardScannerActivity
import com.isitechproject.businesscardscanner.utils.ImageAnalyzerForBusinessCards
import com.isitechproject.businesscardscanner.utils.TextParser
import com.isitechproject.easycardwallet.model.BusinessCard
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.BusinessCardService
import com.isitechproject.easycardwallet.screens.EasyCardWalletAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import java.util.concurrent.ExecutorService
import javax.inject.Inject

@HiltViewModel
class ScanBusinessCardViewModel @Inject constructor(
    private val accountService: AccountService,
    private val businessCardService: BusinessCardService
): EasyCardWalletAppViewModel(accountService) {
    val cardId = mutableStateOf("")

    fun saveBusinessCardTemplate(businessCard: BusinessCard) {
        val businessCardToSave = businessCard.copy(uid = accountService.currentUserId)
        runBlocking {
            cardId.value = businessCardService.create(businessCardToSave)
        }
    }

    fun cameraListener(
        cameraExecutor: ExecutorService,
        cameraProviderFuture: ListenableFuture<ProcessCameraProvider>,
        previewView: PreviewView,
        activity: AppCompatActivity,
        handleText: (Text, String) -> Unit = { _, _ -> }
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
                        handleText,
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
                activity,
                cameraSelector,
                preview,
                imageAnalyzer,
            )

        } catch (exc: Exception) {
            exc.printStackTrace()
        }
    }

    fun parseText(text: Text): Map<String, String> {
        if (text.textBlocks.count() > 1) {
            return TextParser().parseBlocks(text.textBlocks)
        }
        return mapOf(Pair(BusinessCard.NAME_FIELD, text.text))
    }
}