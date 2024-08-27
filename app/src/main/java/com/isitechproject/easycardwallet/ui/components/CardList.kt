package com.isitechproject.easycardwallet.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.isitechproject.easycardwallet.model.AbstractCard

@Composable
fun CardList(
    cards: List<AbstractCard>,
    onCardClick: (AbstractCard) -> Unit,
    currentUserId: String,
    getUserPicture: (String) -> String,
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
                cardName = cards[index].name,
                cardPicture = cards[index].picture,
                onCardClick = { onCardClick(cards[index]) },
                userPicture = if (cards[index].uid === currentUserId) { "" } else getUserPicture(cards[index].uid)
            )
        }
    }
}
