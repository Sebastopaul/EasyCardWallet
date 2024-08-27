package com.isitechproject.businesscardscanner.utils

import android.content.Context
import android.graphics.Rect
import android.graphics.RectF
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class ImageAnalyzerForBusinessCardsWithBoxView(
    private val context: Context,
    private val boxView: BoxView,
    private val previewViewWidth: Float,
    private val previewViewHeight: Float,
    private val handleText: (Text, String) -> Unit = { visionText, _ ->
        Toast.makeText(
            context,
            "Value: " + visionText.text,
            Toast.LENGTH_LONG
        ).show()
    }
) : ImageAnalysis.Analyzer {
    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    /**
     * This parameters will handle preview box scaling
     */
    private var scaleX = 1.5f
    private var scaleY = 1f

    private fun translateX(x: Float) = x * scaleX
    private fun translateY(y: Float) = y * scaleY

    private fun adjustBoundingRect(rect: Rect) = RectF(
        translateX(rect.left.toFloat()),
        translateY(rect.top.toFloat()),
        translateX(rect.right.toFloat()),
        translateY(rect.bottom.toFloat())
    )

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(image: ImageProxy) {
        val img = image.image

        if (img != null) {
            scaleX = previewViewWidth / img.height.toFloat()
            scaleY = previewViewHeight / img.width.toFloat()

            val inputImage = InputImage.fromMediaImage(img, image.imageInfo.rotationDegrees)

            recognizer.process(inputImage)
                .addOnSuccessListener { text ->
                    if (text.text.isNotEmpty()) {
                        for (textBlock in text.textBlocks) {
                            textBlock.boundingBox?.let { rect ->
                                boxView.setRect(
                                    adjustBoundingRect(
                                        rect
                                    )
                                )
                            }
                        }
                    } else {
                        boxView.setRect(RectF())
                    }
                }
                .addOnFailureListener { }
        }
        image.close()
    }
}
