package com.isitechproject.barcodescanner

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.mlkit.vision.barcode.common.Barcode
import com.isitechproject.barcodescanner.screens.createloyaltycard.CreateLoyaltyCardScreen
import com.isitechproject.barcodescanner.screens.scanloyaltycard.ScanLoyaltyCardScreen
import com.isitechproject.easycardwallet.EasyCardWalletActivity
import com.isitechproject.easycardwallet.EasyCardWalletAppState
import com.isitechproject.easycardwallet.LOYALTY_CARD_DEFAULT_ID
import com.isitechproject.easycardwallet.LOYALTY_CARD_ID
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
        ScanLoyaltyCardScreen(
            activity,
            { route -> appState.navigate(route) },
        )
    }

    composable(
        route = "$CREATE_LOYALTY_CARD_SCREEN$BASE64_BARCODE_ARG",
        arguments = listOf(
            navArgument(BASE64_BARCODE_ARG_NAME) { defaultValue = BASE64_BARCODE_DEFAULT },
            navArgument(BARCODE_FORMAT) {
                defaultValue = BARCODE_FORMAT_DEFAULT
                type = NavType.IntType
            },
        )
    ) {
        activity.shutdownCamera()
        CreateLoyaltyCardScreen(
            base64Barcode = Uri.decode(it.arguments?.getString(BASE64_BARCODE_ARG_NAME)) ?: BASE64_BARCODE_DEFAULT,
            barcodeFormat = it.arguments?.getInt(BARCODE_FORMAT) ?: BARCODE_FORMAT_DEFAULT,
            backToMain = { route -> appState.navigate(route) }
        )
    }
}