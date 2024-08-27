package com.isitechproject.easycardwallet.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.isitechproject.easycardwallet.model.User
import com.isitechproject.easycardwallet.utils.ImageConverterBase64

@Composable
fun SharedCard(
    cardName: String,
    userId: String,
    getUser: (String) -> User,
    stopSharing: () -> Unit,
) {
    val user = remember { mutableStateOf(getUser(userId)) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = ShapeDefaults.Medium,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                bitmap = ImageConverterBase64.from(user.value.profilePicture).asImageBitmap(),
                contentDescription = user.value.email
            )

            Spacer(modifier = Modifier.size(12.dp))

            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = cardName,
                    modifier = Modifier.padding(start = 4.dp),
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = user.value.email,
                    modifier = Modifier.padding(start = 4.dp),
                )
            }

            Spacer(modifier = Modifier.size(12.dp))

            IconButton(onClick = stopSharing) {
                Icon(Icons.Filled.Delete, "Stop this share")
            }
        }
    }
}