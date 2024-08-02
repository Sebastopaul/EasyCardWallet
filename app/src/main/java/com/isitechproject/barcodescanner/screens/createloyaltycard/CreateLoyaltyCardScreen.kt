package com.isitechproject.barcodescanner.screens.createloyaltycard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.zxing.BarcodeFormat
import com.isitechproject.barcodescanner.utils.generateBarcode
import com.isitechproject.easycardwallet.ui.components.CardListComponent
import com.isitechproject.easycardwallet.R
import com.isitechproject.easycardwallet.ui.components.BasicStructure
import com.isitechproject.easycardwallet.ui.components.BitmapImage
import com.isitechproject.easycardwallet.ui.theme.EasyCardWalletTheme
import com.isitechproject.easycardwallet.utils.ImageConverterBase64


@Composable
fun CreateLoyaltyCardScreen(
    base64Barcode: String,
    barcodeFormat: Int,
    backToMain: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CreateLoyaltyCardViewModel = hiltViewModel()
) {
    BasicStructure(
        restartApp = {},
        viewModel = viewModel,
        modifier = modifier,
    ) {
        Column {
            Text(text = "Add loyalty card")

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
            )

            BitmapImage(
                bitmap = generateBarcode(Base64.decode),
                modifier = Modifier.fillMaxSize()
            )

            TextField(value = "", onValueChange = { viewModel.updateLoyaltyCardName(it) })

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp))

            Button(
                onClick = { viewModel.onRegisterClick(base64Barcode, backToMain) },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp)
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

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun LoyaltyCardsListPreview() {
    EasyCardWalletTheme {
        CreateLoyaltyCardScreen("", {})
    }
}