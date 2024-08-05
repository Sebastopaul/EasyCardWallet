package com.isitechproject.barcodescanner.utils

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder

fun generateBarcode(data: String, format: BarcodeFormat): Bitmap {
    val multiFormatWriter = MultiFormatWriter()
    return try {
        val bitMatrix: BitMatrix = multiFormatWriter.encode(data, format, 600, 300)
        val barcodeEncoder = BarcodeEncoder()
        barcodeEncoder.createBitmap(bitMatrix)
    } catch (e: WriterException) {
        e.printStackTrace()
        Bitmap.createBitmap(600, 300, Bitmap.Config.ARGB_8888) // Image vide en cas d'erreur
    }
}