package com.isitechproject.barcodescanner

import com.google.mlkit.vision.barcode.common.Barcode

const val CREATE_LOYALTY_CARD_SCREEN = "CreateLoyaltyCardScreen"
const val EASY_CARD_WALLET_MAIN_SCREEN = "EasyCardWalletMainScreen"

const val BASE64_BARCODE_ARG_NAME = "base64Barcode"
const val BARCODE_FORMAT = "barcodeFormat"
const val BASE64_BARCODE_DEFAULT = ""
const val BARCODE_FORMAT_DEFAULT = Barcode.FORMAT_UNKNOWN
const val BASE64_BARCODE_ARG = "?$BASE64_BARCODE_ARG_NAME={$BASE64_BARCODE_ARG_NAME}&$BARCODE_FORMAT={$BARCODE_FORMAT}"
