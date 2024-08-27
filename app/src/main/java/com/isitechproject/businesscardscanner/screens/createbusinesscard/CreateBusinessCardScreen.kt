package com.isitechproject.businesscardscanner.screens.createbusinesscard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.isitechproject.barcodescanner.utils.generateBarcode
import com.isitechproject.easycardwallet.ui.components.BasicStructure
import com.isitechproject.easycardwallet.utils.ImageConverterBase64


@Composable
fun CreateBusinessCardScreen(
    analyzedText: String,
    cardPicture: String,
    backToMain: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CreateBusinessCardViewModel = hiltViewModel()
) {
    val companyName = viewModel.companyName.collectAsState()
    val contactFirstname = viewModel.contactFirstname.collectAsState()
    val contactLastname = viewModel.contactLastname.collectAsState()
    val contactPhone = viewModel.contactPhone.collectAsState()
    val contactMobile = viewModel.contactMobile.collectAsState()
    val contactEmail = viewModel.contactEmail.collectAsState()

    BasicStructure(
        restartApp = {},
        switchScreen = {},
        viewModel = viewModel,
        modifier = modifier,
    ) {
        Column(modifier.fillMaxSize()) {
            Text(
                text = "Add a business card",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Button(onClick = { viewModel.onRegisterClick(cardPicture, backToMain) }) {

            }
        }
    }
}
