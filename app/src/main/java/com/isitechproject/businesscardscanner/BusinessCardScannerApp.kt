package com.isitechproject.businesscardscanner

import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.isitechproject.barcodescanner.BARCODE_FORMAT
import com.isitechproject.barcodescanner.BARCODE_FORMAT_DEFAULT
import com.isitechproject.businesscardscanner.screens.createbusinesscard.CreateBusinessCardScreen
import com.isitechproject.businesscardscanner.screens.scanbusinesscard.ScanBusinessCardScreen
import com.isitechproject.easycardwallet.EasyCardWalletActivity
import com.isitechproject.easycardwallet.EasyCardWalletAppState
import com.isitechproject.easycardwallet.SCAN_LOYALTY_CARD_SCREEN
import com.isitechproject.easycardwallet.ui.theme.EasyCardWalletTheme

@Composable
fun BarcodeScannerApp(activity: BusinessCardScannerActivity) {
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
    activity: BusinessCardScannerActivity
) {
     activity(EASY_CARD_WALLET_MAIN_SCREEN){
        activityClass = EasyCardWalletActivity::class
    }

    composable(SCAN_LOYALTY_CARD_SCREEN) {
        ScanBusinessCardScreen(
            activity,
            { route -> appState.navigate(route) },
        )
    }

    composable(
        route = "$CREATE_BUSINESS_CARD_SCREEN$ANALYZED_TEXT_ARG",
        arguments = listOf(
            navArgument(ANALYZED_TEXT) { defaultValue = BUSINESS_CARD_DEFAULT },
            navArgument(BUSINESS_CARD_PICTURE) { defaultValue = BUSINESS_CARD_DEFAULT },
        ),
    ) {
        activity.shutdownCamera()
        CreateBusinessCardScreen(
            analyzedText = Uri.decode(it.arguments?.getString(ANALYZED_TEXT)) ?: BUSINESS_CARD_DEFAULT,
            cardPicture = Uri.decode(it.arguments?.getString(BUSINESS_CARD_PICTURE)) ?: BUSINESS_CARD_DEFAULT,
            backToMain = { route -> appState.navigate(route) },
        )
    }
}