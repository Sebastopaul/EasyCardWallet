package com.isitechproject.businesscardscanner.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis.Analyzer
import androidx.camera.core.ImageProxy
import androidx.camera.view.transform.ImageProxyTransformFactory
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.isitechproject.easycardwallet.utils.ImageConverterBase64


class ImageAnalyzerForBusinessCards(
    private val context: Context,
    private val handleText: (Text) -> Unit = { visionText ->
        Toast.makeText(
            context,
            "Value: " + visionText.text,
            Toast.LENGTH_LONG
        ).show()
    }) : Analyzer {
    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(image: ImageProxy) {
        val img = image.image ?: return
        val inputImage = InputImage.fromMediaImage(img, image.imageInfo.rotationDegrees)

        recognizer.process(inputImage)
            .addOnSuccessListener { visionText ->
                if (visionText.text.length > 5) {
                    handleText(visionText)
                }
            }
        image.close()
    }

}