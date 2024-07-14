package com.isitechproject.easycardwallet.screens.loyaltycards.loyaltycardslistscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.isitechproject.easycardwallet.ui.components.CardListComponent
import com.isitechproject.easycardwallet.R
import com.isitechproject.easycardwallet.ui.components.BasicStructure
import com.isitechproject.easycardwallet.ui.theme.EasyCardWalletTheme


@Composable
fun LoyaltyCardsListScreen(
    restartApp: (String) -> Unit,
    openScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoyaltyCardsListViewModel = hiltViewModel()
) {
    BasicStructure(
        restartApp = restartApp,
        viewModel = viewModel,
        modifier = modifier,
        title = stringResource(R.string.title_home_page)
    ) {
        val loyaltyCards by viewModel.loyaltyCards.collectAsState(emptyList())

        CardListComponent(
            cards = loyaltyCards,
            onAddCardClick = { viewModel.onAddClick(openScreen) }
        )
    }
}


@Preview(showBackground = true, showSystemUi = true, apiLevel = 34)
@Composable
fun LoyaltyCardsListPreview() {
    EasyCardWalletTheme {
        LoyaltyCardsListScreen({ }, { })
    }
}