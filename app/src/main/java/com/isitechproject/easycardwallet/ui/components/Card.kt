package com.isitechproject.easycardwallet.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.isitechproject.easycardwallet.model.LoyaltyCard
import com.isitechproject.easycardwallet.utils.ImageConverterBase64

@Composable
fun Card(
    card: LoyaltyCard,
    modifier: Modifier = Modifier,
    onCardClick: () -> Unit = {},
) {
    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp),
        onClick = onCardClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            BitmapImage(
                bitmap = ImageConverterBase64.from(card.picture.content),
            )
        }
    }
}