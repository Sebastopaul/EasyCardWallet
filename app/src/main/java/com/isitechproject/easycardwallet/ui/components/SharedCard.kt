package com.isitechproject.easycardwallet.ui.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.isitechproject.easycardwallet.R

@Composable
fun SharedCard(
    cardName: String,
    userEmail: String,
    stopSharing: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = ShapeDefaults.Medium
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = cardName,
                    modifier = Modifier.padding(start = 4.dp),
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = userEmail,
                    modifier = Modifier.padding(start = 4.dp),
                )
            }

            Spacer(modifier = Modifier.size(24.dp))

            IconButton(onClick = stopSharing) {
                Icon(Icons.Filled.Delete, stringResource(R.string.stop_sharing))
            }
        }
    }
}