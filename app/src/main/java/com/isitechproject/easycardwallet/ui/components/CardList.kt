package com.isitechproject.easycardwallet.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isitechproject.easycardwallet.R
import com.isitechproject.easycardwallet.model.AbstractCard
import com.isitechproject.easycardwallet.model.LoyaltyCard
import com.isitechproject.easycardwallet.screens.loyaltycards.loyaltycardslistscreen.LoyaltyCardsListViewModel
import com.isitechproject.easycardwallet.ui.theme.EasyCardWalletTheme

@Composable
fun CardListComponent(
    cards: List<AbstractCard>,
    openScreen: (String) -> Unit,
    viewModel: LoyaltyCardsListViewModel,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(cards.size) { index ->
            Card(
                card = cards[index],
                onCardClick = { viewModel.onLoyaltyCardClick(openScreen, cards[index]) }
            )
        }
    }
}
