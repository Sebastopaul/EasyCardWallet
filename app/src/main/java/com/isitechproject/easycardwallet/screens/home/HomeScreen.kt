package com.isitechproject.easycardwallet.screens.home

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.FirebaseApp
import com.isitechproject.easycardwallet.ui.components.CardListComponent
import com.isitechproject.easycardwallet.ui.components.TopAppBarComponent
import com.isitechproject.easycardwallet.R
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.LoyaltyCardService
import com.isitechproject.easycardwallet.model.service.impl.AccountServiceImpl
import com.isitechproject.easycardwallet.model.service.impl.LoyaltyCardServiceImpl
import com.isitechproject.easycardwallet.model.service.impl.UserDataServiceImpl
import com.isitechproject.easycardwallet.ui.theme.EasyCardWalletTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    restartApp: (String) -> Unit,
    openScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) { viewModel.initialize(restartApp) }
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Text("Menu", modifier = Modifier.padding(16.dp))
                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text(text = "Cards") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Loyalty") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
                // ...other drawer items
            }
        }
    ) {
        //screen Content
        Scaffold {
            var showExitAppDialog by remember { mutableStateOf(false) }
            var showRemoveAccDialog by remember { mutableStateOf(false) }

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

                CardListComponent(
                    title = stringResource(R.string.title_home_page),
                    cards = viewModel.getLoyaltyCards(),
                    onAddCardClick = { viewModel.toggleDrawer() },
                    onCardClick = { showExitAppDialog = true }
                )


                // Message sign out
                if (showExitAppDialog) {
                    AlertDialog(
                        title = { Text(stringResource(R.string.sign_out_title)) },
                        text = { Text(stringResource(R.string.sign_out_description)) },
                        dismissButton = {
                            Button(onClick = { showExitAppDialog = false }) {
                                Text(text = stringResource(R.string.cancel))
                            }
                        },
                        confirmButton = {
                            Button(onClick = {
                                viewModel.onSignOutClick()
                                showExitAppDialog = false
                            }) {
                                Text(text = stringResource(R.string.sign_out))
                            }
                        },
                        onDismissRequest = { showExitAppDialog = false }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true, apiLevel = 34)
@Composable
fun HomePreview() {
    val accountService = AccountServiceImpl()

    EasyCardWalletTheme {
        HomeScreen({ }, { }, viewModel = HomeViewModel(accountService, LoyaltyCardServiceImpl(UserDataServiceImpl(accountService))))
    }
}