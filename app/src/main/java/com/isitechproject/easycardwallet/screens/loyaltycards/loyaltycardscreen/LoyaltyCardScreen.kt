package com.isitechproject.easycardwallet.screens.loyaltycards.loyaltycardscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.isitechproject.easycardwallet.model.service.impl.AccountServiceImpl
import com.isitechproject.easycardwallet.model.service.impl.LoyaltyCardServiceImpl
import com.isitechproject.easycardwallet.model.service.impl.UserServiceImpl
import com.isitechproject.easycardwallet.ui.components.BasicStructure
import com.isitechproject.easycardwallet.ui.theme.EasyCardWalletTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoyaltyCardScreen(
    loyaltyCardId: String,
    popUpScreen: () -> Unit,
    restartApp: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoyaltyCardViewModel = hiltViewModel()
) {
    val loyaltyCard = viewModel.loyaltyCard.collectAsState()

    LaunchedEffect(Unit) { viewModel.initialize(loyaltyCardId, restartApp) }

    BasicStructure(
        restartApp = restartApp,
        viewModel = viewModel,
        modifier = modifier,
    ) {

        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {
            TopAppBar(
                title = { Text(loyaltyCard.value.name) },
                actions = {
                    IconButton(onClick = { viewModel.saveLoyaltyCard(popUpScreen) }) {
                        Icon(Icons.Filled.Done, "Save loyalty card")
                    }
                    IconButton(onClick = { viewModel.deleteLoyaltyCard(popUpScreen) }) {
                        Icon(Icons.Filled.Delete, "Delete loyalty card")
                    }
                }
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = loyaltyCard.value.name,
                    onValueChange = { viewModel.updateLoyaltyCard() },
                    modifier = modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true, apiLevel = 34)
@Composable
fun LoyaltyCardsListPreview() {
    val accountService = AccountServiceImpl()

    EasyCardWalletTheme {
        LoyaltyCardScreen("", { }, { }, viewModel = LoyaltyCardViewModel(accountService, LoyaltyCardServiceImpl(UserServiceImpl(accountService))))
    }
}