package com.isitechproject.businesscardscanner.screens.createbusinesscard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
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
import com.isitechproject.easycardwallet.ui.components.BasicStructure
import com.isitechproject.easycardwallet.utils.ImageConverterBase64


@Composable
fun CreateBusinessCardScreen(
    businessCard: String,
    backToMain: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CreateBusinessCardViewModel = hiltViewModel()
) {
    val companyName = viewModel.companyName.collectAsState()
    val address = viewModel.address.collectAsState()
    val zip = viewModel.zip.collectAsState()
    val city = viewModel.city.collectAsState()
    val contactFirstname = viewModel.contactFirstname.collectAsState()
    val contactLastname = viewModel.contactLastname.collectAsState()
    val contactPhone = viewModel.contactPhone.collectAsState()
    val contactMobile = viewModel.contactMobile.collectAsState()
    val contactEmail = viewModel.contactEmail.collectAsState()
    val picture = viewModel.picture.collectAsState()

    viewModel.initializeData(businessCard)

    BasicStructure(
        restartApp = {},
        switchScreen = {},
        viewModel = viewModel,
        modifier = modifier,
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            Text(
                text = "Add a business card",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Image(
                bitmap = ImageConverterBase64.fromBase64String(picture.value).asImageBitmap(),
                contentDescription = "Picture taken",
            )

            Spacer(modifier = Modifier.padding(12.dp))

            BusinessCardTextField(
                value = companyName.value,
                onValueChange = { viewModel.updateCompanyName(it) },
                placeholder = { Text("Company") },
                leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "Company") },
            )

            Spacer(modifier = Modifier.padding(12.dp))

            BusinessCardTextField(
                value = contactFirstname.value,
                onValueChange = { viewModel.updateContactFirstname(it) },
                placeholder = { Text("Contact Firstname") },
                leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "Contact Firstname") },
            )

            BusinessCardTextField(
                value = contactLastname.value,
                onValueChange = { viewModel.updateContactLastname(it) },
                placeholder = { Text("Contact Lastname") },
                leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "Contact Lastname") },
            )

            Spacer(modifier = Modifier.padding(12.dp))

            BusinessCardTextField(
                value = contactEmail.value,
                onValueChange = { viewModel.updateContactEmail(it) },
                placeholder = { Text("Contact Email") },
                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Contact Email") },
            )

            BusinessCardTextField(
                value = contactPhone.value,
                onValueChange = { viewModel.updateContactPhone(it) },
                placeholder = { Text("Contact Phone") },
                leadingIcon = { Icon(imageVector = Icons.Default.Phone, contentDescription = "Contact Phone") },
            )

            BusinessCardTextField(
                value = contactMobile.value,
                onValueChange = { viewModel.updateContactMobile(it) },
                placeholder = { Text("Contact Mobile") },
                leadingIcon = { Icon(imageVector = Icons.Default.Phone, contentDescription = "Contact Mobile") },
            )

            Spacer(modifier = Modifier.padding(12.dp))

            BusinessCardTextField(
                value = address.value,
                onValueChange = { viewModel.updateAddress(it) },
                placeholder = { Text("Address") },
                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Address") },
            )

            BusinessCardTextField(
                value = zip.value,
                onValueChange = { viewModel.updateZip(it) },
                placeholder = { Text("Zip Code") },
                leadingIcon = { Icon(imageVector = Icons.Default.Phone, contentDescription = "Zip Code") },
            )

            BusinessCardTextField(
                value = city.value,
                onValueChange = { viewModel.updateCity(it) },
                placeholder = { Text("City") },
                leadingIcon = { Icon(imageVector = Icons.Default.Phone, contentDescription = "City") },
            )

            Button(
                onClick = { viewModel.onRegisterClick(picture.value, backToMain) },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Register business card",
                    fontSize = 16.sp,
                    modifier = modifier.padding(0.dp, 6.dp)
                )
            }
        }
    }
}

@Composable
fun BusinessCardTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: @Composable() (() -> Unit)? = null,
    leadingIcon: @Composable() (() -> Unit)? = null,
) {
    OutlinedTextField(
        singleLine = true,
        modifier = Modifier
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
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholder,
        leadingIcon = leadingIcon
    )
}
