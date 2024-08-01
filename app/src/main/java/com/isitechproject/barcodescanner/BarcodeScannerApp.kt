package com.isitechproject.barcodescanner

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.isitechproject.barcodescanner.screens.scanloyaltycard.ScanLoyaltyCardScreen
import com.isitechproject.easycardwallet.EasyCardWalletActivity
import com.isitechproject.easycardwallet.EasyCardWalletAppState
import com.isitechproject.easycardwallet.SCAN_LOYALTY_CARD_SCREEN
import com.isitechproject.easycardwallet.ui.theme.EasyCardWalletTheme

@Composable
fun BarcodeScannerApp(activity: BarcodeScannerActivity) {
    EasyCardWalletTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val appState = rememberAppState()

            Scaffold { innerPaddingModifier ->
                NavHost(
                    navController = appState.navController,
                    startDestination = SCAN_LOYALTY_CARD_SCREEN,
                    modifier = Modifier.padding(innerPaddingModifier)
                ) {
                    barcodeScannerGraph(appState, activity)
                }
            }
        }
    }
}

@Composable
fun rememberAppState(navController: NavHostController = rememberNavController()) =
    remember(navController) {
        EasyCardWalletAppState(navController)
    }

fun NavGraphBuilder.barcodeScannerGraph(
    appState: EasyCardWalletAppState,
    activity: BarcodeScannerActivity
) {
     activity(EASY_CARD_WALLET_MAIN_SCREEN){
        activityClass = EasyCardWalletActivity::class
    }

    composable(SCAN_LOYALTY_CARD_SCREEN) {
        ScanLoyaltyCardScreen(activity)
    }
}