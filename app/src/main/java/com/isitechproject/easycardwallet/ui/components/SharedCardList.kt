package com.isitechproject.easycardwallet.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.isitechproject.easycardwallet.model.AbstractCard
import com.isitechproject.easycardwallet.model.AbstractSharedCard

@Composable
fun SharedCardList(
    sharedCardsList: List<AbstractSharedCard>,
    cardsList: List<AbstractCard>,
    getUserEmail: (String) -> String,
    stopSharing: (String) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(sharedCardsList) { sharedCard ->
            SharedCard(
                cardName = cardsList.first { it.id == sharedCard.sharedCardId }.name,
                userEmail = getUserEmail(sharedCard.sharedUid),
                stopSharing = { stopSharing(sharedCard.id) }
            )
        }
    }
}