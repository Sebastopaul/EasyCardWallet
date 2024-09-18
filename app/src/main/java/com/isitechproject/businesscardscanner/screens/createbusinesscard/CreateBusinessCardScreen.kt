package com.isitechproject.businesscardscanner.screens.createbusinesscard

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.LocationOn
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.isitechproject.easycardwallet.R
import com.isitechproject.easycardwallet.model.BusinessCard
import com.isitechproject.easycardwallet.ui.components.BasicStructure


@Composable
fun CreateBusinessCardScreen(
    businessCard: BusinessCard,
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

    viewModel.initializeData(businessCard)

    BasicStructure(
        restartApp = {},
        switchScreen = {},
        showBottomBar = false,
        viewModel = viewModel,
        modifier = modifier,
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            Text(
                text = stringResource(R.string.add_business_card),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            //Image(
            //    bitmap = ImageConverterBase64.fromBase64String(picture.value).asImageBitmap(),
            //    contentDescription = "Picture taken",
            //)

            Spacer(modifier = Modifier.padding(12.dp))

            BusinessCardTextField(
                value = companyName.value,
                onValueChange = { viewModel.updateCompanyName(it) },
                placeholder = { Text(stringResource(R.string.company)) },
                leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = stringResource(R.string.company)) },
            )

            Spacer(modifier = Modifier.padding(12.dp))

            BusinessCardTextField(
                value = contactFirstname.value,
                onValueChange = { viewModel.updateContactFirstname(it) },
                placeholder = { Text(stringResource(R.string.contact_firstname)) },
                leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = stringResource(R.string.contact_firstname)) },
            )

            BusinessCardTextField(
                value = contactLastname.value,
                onValueChange = { viewModel.updateContactLastname(it) },
                placeholder = { Text(stringResource(R.string.contact_lastname)) },
                leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = stringResource(R.string.contact_lastname)) },
            )

            Spacer(modifier = Modifier.padding(12.dp))

            BusinessCardTextField(
                value = contactEmail.value,
                onValueChange = { viewModel.updateContactEmail(it) },
                placeholder = { Text(stringResource(R.string.contact_email)) },
                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = stringResource(R.string.contact_email)) },
            )

            BusinessCardTextField(
                value = contactPhone.value,
                onValueChange = { viewModel.updateContactPhone(it) },
                placeholder = { Text(stringResource(R.string.contact_phone)) },
                leadingIcon = { Icon(imageVector = Icons.Default.Phone, contentDescription = stringResource(R.string.contact_phone)) },
            )

            BusinessCardTextField(
                value = contactMobile.value,
                onValueChange = { viewModel.updateContactMobile(it) },
                placeholder = { Text(stringResource(R.string.contact_mobile)) },
                leadingIcon = { Icon(imageVector = Icons.Default.Phone, contentDescription = stringResource(R.string.contact_mobile)) },
            )

            Spacer(modifier = Modifier.padding(12.dp))

            BusinessCardTextField(
                value = address.value,
                onValueChange = { viewModel.updateAddress(it) },
                placeholder = { Text(stringResource(R.string.address)) },
                leadingIcon = { Icon(imageVector = Icons.Default.LocationOn, contentDescription = stringResource(R.string.address)) },
            )

            BusinessCardTextField(
                value = zip.value,
                onValueChange = { viewModel.updateZip(it) },
                placeholder = { Text(stringResource(R.string.zip_code)) },
                leadingIcon = { Icon(imageVector = Icons.Default.LocationOn, contentDescription = stringResource(R.string.zip_code)) },
            )

            BusinessCardTextField(
                value = city.value,
                onValueChange = { viewModel.updateCity(it) },
                placeholder = { Text(stringResource(R.string.city)) },
                leadingIcon = { Icon(imageVector = Icons.Default.LocationOn, contentDescription = stringResource(R.string.city)) },
            )

            Spacer(modifier = Modifier.padding(12.dp))

            Button(
                onClick = { viewModel.onRegisterClick(backToMain) },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.register_business_card),
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
