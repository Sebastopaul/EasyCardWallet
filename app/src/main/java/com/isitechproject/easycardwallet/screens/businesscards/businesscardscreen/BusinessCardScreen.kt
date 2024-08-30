package com.isitechproject.easycardwallet.screens.businesscards.businesscardscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.isitechproject.easycardwallet.R
import com.isitechproject.easycardwallet.ui.components.BasicStructure
import com.isitechproject.easycardwallet.utils.ImageConverterBase64


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessCardScreen(
    businessCardId: String,
    popUpScreen: () -> Unit,
    restartApp: (String) -> Unit,
    switchScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BusinessCardViewModel = hiltViewModel()
) {
    val businessCard = viewModel.businessCard.collectAsState()
    var showShareCardDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { viewModel.initialize(businessCardId, restartApp) }

    BasicStructure(
        restartApp = restartApp,
        switchScreen = switchScreen,
        viewModel = viewModel,
        modifier = modifier,
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {
            TopAppBar(
                title = {
                    TextField(
                        value = businessCard.value.name,
                        onValueChange = { viewModel.updateBusinessCard(it) },
                    )
                },
                actions = {
                    if (viewModel.isUserProperty()) {
                        IconButton(onClick = { showShareCardDialog = true }) {
                            Icon(Icons.Filled.Share, "Share business card")
                        }
                        IconButton(onClick = { viewModel.saveBusinessCard(popUpScreen) }) {
                            Icon(Icons.Filled.Done, "Save business card")
                        }
                        IconButton(onClick = { viewModel.deleteBusinessCard(popUpScreen) }) {
                            Icon(Icons.Filled.Delete, "Delete business card")
                        }
                    } else {
                        IconButton(onClick = { viewModel.deleteSharedBusinessCard(popUpScreen) }) {
                            Icon(Icons.Filled.Delete, "Delete shared business card")
                        }
                    }
                }
            )

            Image(
                bitmap = ImageConverterBase64.fromBase64String(businessCard.value.picture).asImageBitmap(),
                contentDescription = "QR Code",
                modifier = Modifier.fillMaxSize()
            )

            if (showShareCardDialog) {
                val emailToShare = viewModel.emailToShare.collectAsState()

                AlertDialog(
                    title = { Text("Enter the email of the user you want to share this card with.") },
                    text = {
                        TextField(
                            value = emailToShare.value,
                            onValueChange = { viewModel.updateEmailToShare(it) },
                            label = { Text(text = "Email") },
                        )
                    },
                    dismissButton = {
                        Button(onClick = {
                            viewModel.updateEmailToShare("")
                            showShareCardDialog = false
                        }) {
                            Text(text = stringResource(R.string.cancel))
                        } },
                    confirmButton = {
                        Button(onClick = {
                            viewModel.shareBusinessCard()
                            viewModel.updateEmailToShare("")
                            showShareCardDialog = false
                        }) {
                            Text(text = "OK")
                        } },
                    onDismissRequest = {
                        viewModel.updateEmailToShare("")
                        showShareCardDialog = false
                    }
                )
            }
        }
    }
}
