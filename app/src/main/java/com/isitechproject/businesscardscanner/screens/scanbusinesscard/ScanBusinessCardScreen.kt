package com.isitechproject.businesscardscanner.screens.scanbusinesscard

import android.net.Uri
import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import android.widget.Toast
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.isitechproject.barcodescanner.BARCODE_ARG_NAME
import com.isitechproject.barcodescanner.BARCODE_FORMAT
import com.isitechproject.barcodescanner.CREATE_LOYALTY_CARD_SCREEN
import com.isitechproject.barcodescanner.screens.scanloyaltycard.getBarcodeValue
import com.isitechproject.businesscardscanner.BUSINESS_CARD_ARG
import com.isitechproject.businesscardscanner.BusinessCardScannerActivity
import com.isitechproject.businesscardscanner.CREATE_BUSINESS_CARD_SCREEN
import com.isitechproject.easycardwallet.model.BusinessCard
import com.isitechproject.easycardwallet.model.service.impl.ID_FIELD
import com.isitechproject.easycardwallet.utils.ImageConverterBase64

@Composable
fun ScanBusinessCardScreen(
    openCreationScreen: (String) -> Unit,
    viewModel: ScanBusinessCardViewModel = hiltViewModel()
) {
    val context = LocalContext.current as BusinessCardScannerActivity
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

    Surface(color = MaterialTheme.colorScheme.background) {
        AndroidView(
            factory = { previewContext ->
                PreviewView(previewContext).apply {
                    layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                    scaleType = PreviewView.ScaleType.FILL_START
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE

                    post {
                        cameraProviderFuture.addListener(
                            {
                                viewModel.cameraListener(
                                    context.cameraExecutor,
                                    cameraProviderFuture,
                                    this,
                                    context,
                                ) { text, picture ->
                                    val parsedTextBlocks = viewModel.parseText(text)

                                    Log.d("TEST", "PASS_HANDLER")
                                    Log.d("TEST", text.text)
                                    for (parsedText in viewModel.parseText(text)) {
                                        Log.d("TEST", "${parsedText.key}: ${parsedText.value}")
                                    }
                                    Log.d("TEST", BUSINESS_CARD_ARG)

                                    viewModel.saveBusinessCardTemplate(
                                        BusinessCard(
                                            companyName = parsedTextBlocks[BusinessCard.COMPANY_NAME_FIELD].orEmpty(),
                                            contactFirstname = parsedTextBlocks[BusinessCard.CONTACT_FIRSTNAME_FIELD].orEmpty(),
                                            contactLastname = parsedTextBlocks[BusinessCard.CONTACT_LASTNAME_FIELD].orEmpty(),
                                            contactEmail = parsedTextBlocks[BusinessCard.CONTACT_EMAIL_FIELD].orEmpty(),
                                            contactPhone = parsedTextBlocks[BusinessCard.CONTACT_PHONE_FIELD].orEmpty(),
                                            contactMobile = parsedTextBlocks[BusinessCard.CONTACT_MOBILE_FIELD].orEmpty(),
                                            address = parsedTextBlocks[BusinessCard.ADDRESS_FIELD].orEmpty(),
                                            zip = parsedTextBlocks[BusinessCard.ZIP_FIELD].orEmpty(),
                                            city = parsedTextBlocks[BusinessCard.CITY_FIELD].orEmpty(),
                                            picture = picture,
                                        )
                                    )
                                    openCreationScreen("$CREATE_BUSINESS_CARD_SCREEN?$ID_FIELD=${Uri.encode(viewModel.cardId.value)}")
                                }
                            },
                            ContextCompat.getMainExecutor(previewContext)
                        )
                    }
                }
            }
        )
    }
}