package com.isitechproject.easycardwallet.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.isitechproject.easycardwallet.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarComponent(
    onNavigationIconClick: () -> Unit,
    onActionIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        modifier = modifier
            .height(90.dp),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        navigationIcon = {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = onNavigationIconClick) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = stringResource(R.string.menu),
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        },
        title = {
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.title),
                    contentDescription = stringResource(R.string.topbar_image),
                    modifier = Modifier.height(70.dp)
                )
            }
        },
        actions = {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = onActionIconClick) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = stringResource(R.string.action_icon),
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }
    )
}
