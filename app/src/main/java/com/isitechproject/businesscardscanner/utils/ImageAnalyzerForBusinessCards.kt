package com.isitechproject.businesscardscanner.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis.Analyzer
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
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
        val img = image.image
        if (img == null) {
            image.close()
            return
        }

        val inputImage = InputImage.fromMediaImage(img, image.imageInfo.rotationDegrees)

        recognizer.process(inputImage)
            .addOnSuccessListener { visionText ->
                val bitmapConverted = inputImage.bitmapInternal?.let { ImageConverterBase64.toBase64String(it) } ?: ""
                handleText(visionText, bitmapConverted)
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    context,
                    "Error: " + e.message,
                    Toast.LENGTH_LONG
                ).show()
            }

        image.close()
    }
}