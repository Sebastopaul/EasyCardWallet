package com.isitechproject.easycardwallet.screens.loyaltycards.createloyaltycardscreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.isitechproject.easycardwallet.R
import com.isitechproject.easycardwallet.ui.components.BasicStructure
import com.isitechproject.easycardwallet.ui.theme.EasyCardWalletTheme


@Composable
fun CreateLoyaltyCardScreen(
    restartApp: (String) -> Unit,
    openScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CreateLoyaltyCardViewModel = hiltViewModel(),
) {
    BasicStructure(
        restartApp = restartApp,
        viewModel = viewModel,
        modifier = modifier,
    ) {

    }
}


@Preview(showBackground = true, showSystemUi = true, apiLevel = 34)
@Composable
fun CreateLoyaltyCardPreview() {
    EasyCardWalletTheme {
        CreateLoyaltyCardScreen({ }, { })
    }
}