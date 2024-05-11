package com.isitechproject.easycardwallet.screens.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.isitechproject.easycardwallet.R
import com.isitechproject.easycardwallet.ui.theme.EasyCardWalletTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeScreen(
    restartApp: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) { viewModel.initialize(restartApp) }

    var showExitAppDialog by remember { mutableStateOf(false) }
    var showRemoveAccDialog by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        actions = {
            IconButton(onClick = { showExitAppDialog = true }) {
                Icon(Icons.AutoMirrored.Filled.ExitToApp, stringResource(R.string.exit_app))
            }
            IconButton(onClick = { showRemoveAccDialog = true }) {
                Icon(
                    painter = painterResource(id = R.drawable.person_remove),
                    contentDescription = stringResource(R.string.remove_account),
                )
            }
        },
        modifier = modifier.fillMaxWidth(),
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun HomePreview() {
    EasyCardWalletTheme {
        HomeScreen(restartApp = {})
    }
}