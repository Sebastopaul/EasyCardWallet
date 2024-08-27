package com.isitechproject.easycardwallet.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.isitechproject.easycardwallet.utils.ImageConverterBase64

@Composable
fun Card(
    cardName: String,
    cardPicture: String,
    modifier: Modifier = Modifier,
    userPicture: String = "",
    onCardClick: () -> Unit = {},
) {
    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp),
        onClick = onCardClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (userPicture != "") {
                        Image(
                            bitmap = ImageConverterBase64.from(userPicture).asImageBitmap(),
                            contentDescription = "User picture",
                            modifier = Modifier.size(4.dp, 4.dp)
                        )

                        Spacer(modifier = Modifier.padding(horizontal = 12.dp))
                    }

                    Text(
                        text = cardName,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }

                Image(
                    bitmap = ImageConverterBase64.from(cardPicture).asImageBitmap(),
                    contentDescription = "",
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
