package com.isitechproject.barcodescanner.utils

import com.google.mlkit.vision.barcode.common.Barcode
import com.google.zxing.BarcodeFormat

class MLKitZXingFormatConverter {
    companion object {
        fun from(format: Int): BarcodeFormat {
            return when (format) {
                Barcode.FORMAT_QR_CODE -> BarcodeFormat.QR_CODE
                Barcode.FORMAT_AZTEC -> BarcodeFormat.AZTEC
                Barcode.FORMAT_ITF -> BarcodeFormat.ITF
                Barcode.FORMAT_CODABAR -> BarcodeFormat.CODABAR
                Barcode.FORMAT_CODE_39 -> BarcodeFormat.CODE_39
                Barcode.FORMAT_CODE_128 -> BarcodeFormat.CODE_128
                Barcode.FORMAT_CODE_93 -> BarcodeFormat.CODE_93
                Barcode.FORMAT_DATA_MATRIX -> BarcodeFormat.DATA_MATRIX
                Barcode.FORMAT_EAN_13 -> BarcodeFormat.EAN_13
                Barcode.FORMAT_EAN_8 -> BarcodeFormat.EAN_8
                Barcode.FORMAT_PDF417 -> BarcodeFormat.PDF_417
                Barcode.FORMAT_UPC_A -> BarcodeFormat.UPC_A
                Barcode.FORMAT_UPC_E -> BarcodeFormat.UPC_E
                else -> throw Exception("Wrong format code.")
            }
        }

        fun to(format: BarcodeFormat): Int {
            return when (format) {
                BarcodeFormat.QR_CODE -> Barcode.FORMAT_QR_CODE
                BarcodeFormat.AZTEC -> Barcode.FORMAT_AZTEC
                BarcodeFormat.ITF -> Barcode.FORMAT_ITF
                BarcodeFormat.CODABAR -> Barcode.FORMAT_CODABAR
                BarcodeFormat.CODE_39 -> Barcode.FORMAT_CODE_39
                BarcodeFormat.CODE_128 -> Barcode.FORMAT_CODE_128
                BarcodeFormat.CODE_93 -> Barcode.FORMAT_CODE_93
                BarcodeFormat.DATA_MATRIX -> Barcode.FORMAT_DATA_MATRIX
                BarcodeFormat.EAN_13 -> Barcode.FORMAT_EAN_13
                BarcodeFormat.EAN_8 -> Barcode.FORMAT_EAN_8
                BarcodeFormat.PDF_417 -> Barcode.FORMAT_PDF417
                BarcodeFormat.UPC_A -> Barcode.FORMAT_UPC_A
                BarcodeFormat.UPC_E -> Barcode.FORMAT_UPC_E
                else -> throw Exception("Wrong format code.")
            }
        }
    }
}