package com.isitechproject.barcodescanner.utils

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

class ImageAnalyzerForBarcodes(
    context: Context,
    private val handleBarcode: (Barcode) -> Unit = { barcode ->
        Toast.makeText(
            context,
            "Value: " + barcode.rawValue,
            Toast.LENGTH_SHORT
        ).show()
}) : Analyzer {
    private val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
        .build()

    private val scanner = BarcodeScanning.getClient(options)

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(image: ImageProxy) {
        val img = image.image
        if (img == null) {
            image.close()
            return
        }

        val inputImage = InputImage.fromMediaImage(img, image.imageInfo.rotationDegrees)

        scanner.process(inputImage)
            .addOnSuccessListener { barcodes ->
                for (barcode in barcodes) {
                    handleBarcode(barcode)
                }
            }
            .addOnFailureListener { }

        image.close()
    }
}