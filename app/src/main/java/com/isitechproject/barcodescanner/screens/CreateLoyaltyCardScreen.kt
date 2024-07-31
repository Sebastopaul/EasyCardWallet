package com.isitechproject.barcodescanner.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.isitechproject.easycardwallet.ui.components.CardListComponent
import com.isitechproject.easycardwallet.R
import com.isitechproject.easycardwallet.ui.components.BasicStructure
import com.isitechproject.easycardwallet.ui.theme.EasyCardWalletTheme


@Composable
fun LoyaltyCardsListScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateLoyaltyCardViewModel = hiltViewModel()
) {
    BasicStructure(
        restartApp = {  },
        viewModel = viewModel,
        modifier = modifier,
    ) {
        val loyaltyCards by viewModel.loyaltyCards.collectAsState(emptyList())

        Column {
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
            ) {

                Text(text = stringResource(id = R.string.loyalty_card_list))
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
            )

            CardListComponent(
                cards = loyaltyCards,
                onCardClick = {},
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true, apiLevel = 34)
@Composable
fun LoyaltyCardsListPreview() {
    EasyCardWalletTheme {
        LoyaltyCardsListScreen()
    }
}