package com.isitechproject.easycardwallet.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.isitechproject.easycardwallet.R
import com.isitechproject.easycardwallet.screens.EasyCardWalletAppViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BasicStructure(
    restartApp: (String) -> Unit,
    switchScreen: (String) -> Unit,
    viewModel: EasyCardWalletAppViewModel,
    modifier: Modifier = Modifier,
    showBottomBar: Boolean = true,
    content: @Composable () -> Unit
) {
    var showExitAppDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { viewModel.initialize(restartApp) }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomAppBar(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(onClick = { viewModel.toLoyaltyCardsList(switchScreen) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.loyalty_card_icon_0),
                                contentDescription = "Loyalty cards list",
                            )
                        }
                        IconButton(onClick = { viewModel.toBusinessCardsList(switchScreen) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.business_card_icon),
                                contentDescription = "Business cards list",
                            )
                        }
                    }
                }
            }
        },
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 12.dp)
        ) {
            TopAppBarComponent(
                onNavigationIconClick = { viewModel.toggleDrawer() },
                onActionIconClick = { showExitAppDialog = true }
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )

            content()

            // Message sign out
            if (showExitAppDialog) {
                AlertDialog(
                    title = { Text(stringResource(R.string.sign_out_title)) },
                    text = { Text(stringResource(R.string.sign_out_description)) },
                    dismissButton = {
                        Button(onClick = { showExitAppDialog = false }) {
                            Text(text = stringResource(R.string.cancel))
                        } },
                    confirmButton = {
                        Button(onClick = {
                            viewModel.onSignOutClick(restartApp)
                            showExitAppDialog = false
                        }) {
                            Text(text = stringResource(R.string.sign_out))
                        } },
                    onDismissRequest = { showExitAppDialog = false }
                )
            }
        }
    }
}
