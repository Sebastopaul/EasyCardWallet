package com.isitechproject.barcodescanner.screens.createloyaltycard

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.isitechproject.barcodescanner.BASE64_BARCODE_DEFAULT
import com.isitechproject.barcodescanner.utils.MLKitZXingFormatConverter
import com.isitechproject.barcodescanner.utils.generateBarcode
import com.isitechproject.easycardwallet.R
import com.isitechproject.easycardwallet.ui.components.BasicStructure
import com.isitechproject.easycardwallet.ui.components.BitmapImage
import com.isitechproject.easycardwallet.utils.ImageConverterBase64


@Composable
fun CreateLoyaltyCardScreen(
    barcodeValue: String,
    barcodeFormat: Int,
    backToMain: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CreateLoyaltyCardViewModel = hiltViewModel()
) {
    val bitmap = generateBarcode(barcodeValue, MLKitZXingFormatConverter.from(barcodeFormat))
    val name = viewModel.loyaltyCardName.collectAsState()

    BasicStructure(
        restartApp = {},
        switchScreen = {},
        viewModel = viewModel,
        modifier = modifier,
    ) {
        Column(modifier.fillMaxSize()) {
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp))

            Text(
                text = "Add a loyalty card",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp))

            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "",
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp, 4.dp)
            )

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp))

            Text(
                "Please name this card",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp),
            )

            OutlinedTextField(
                singleLine = true,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp, 4.dp)
                    .border(
                        BorderStroke(width = 2.dp, color = Color.Blue),
                        shape = RoundedCornerShape(50)
                    ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                value = name.value,
                onValueChange = { viewModel.updateLoyaltyCardName(it) },
                placeholder = { Text("Card name") },
                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Email") }
            )

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp))

            Button(
                onClick = { ImageConverterBase64.to(bitmap)
                    ?.let { viewModel.onRegisterClick(it, barcodeValue, backToMain) }
                },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Register loyalty card",
                    fontSize = 16.sp,
                    modifier = modifier.padding(0.dp, 6.dp)
                )
            }
        }
    }
}
