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
    private val handleText: (Text, String) -> Unit = { visionText, _ ->
        Toast.makeText(
            context,
            "Value: " + visionText.text,
            Toast.LENGTH_LONG
        ).show()
    }) : Analyzer {
    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(image: ImageProxy) {
        val bitmap = image.toBitmap()
        val inputImage = InputImage.fromBitmap(bitmap, image.imageInfo.rotationDegrees)

        recognizer.process(inputImage)
            .addOnSuccessListener { visionText ->
                if (visionText.text.length > 5) {
                    handleText(visionText, convertImageToBitmap(inputImage))
                }
            }
        image.close()
    }

    private fun convertImageToBitmap(inputImage: InputImage): String {
        val bitmap = inputImage.bitmapInternal ?: return ""

        return ImageConverterBase64.toBase64String(bitmap) ?: ""
    }
}