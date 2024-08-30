package com.isitechproject.businesscardscanner

import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.isitechproject.businesscardscanner.screens.createbusinesscard.CreateBusinessCardScreen
import com.isitechproject.businesscardscanner.screens.scanbusinesscard.ScanBusinessCardScreen
import com.isitechproject.easycardwallet.EasyCardWalletActivity
import com.isitechproject.easycardwallet.EasyCardWalletAppState
import com.isitechproject.easycardwallet.SCAN_LOYALTY_CARD_SCREEN
import com.isitechproject.easycardwallet.model.BusinessCard
import com.isitechproject.easycardwallet.model.service.impl.ID_FIELD
import com.isitechproject.easycardwallet.ui.theme.EasyCardWalletTheme

@Composable
fun BusinessCardScannerApp(route: String = SCAN_LOYALTY_CARD_SCREEN) {
    EasyCardWalletTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val appState = rememberAppState()

            Scaffold { innerPaddingModifier ->
                NavHost(
                    navController = appState.navController,
                    startDestination = route,
                    modifier = Modifier.padding(innerPaddingModifier)
                ) {
                    barcodeScannerGraph(appState)
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

fun NavGraphBuilder.barcodeScannerGraph(appState: EasyCardWalletAppState, ) {
     activity(EASY_CARD_WALLET_MAIN_SCREEN){
        activityClass = EasyCardWalletActivity::class
    }

    composable(SCAN_LOYALTY_CARD_SCREEN) {
        ScanBusinessCardScreen(
            { route -> appState.navigate(route) },
        )
    }

    composable(
        route = "$CREATE_BUSINESS_CARD_SCREEN$BUSINESS_CARD_ARG",
        arguments = listOf(navArgument(ID_FIELD) { defaultValue = BUSINESS_CARD_DEFAULT }),
    ) {
        val context = LocalContext.current as BusinessCardScannerActivity
        context.shutdownCamera()

        CreateBusinessCardScreen(
            businessCard = Uri.decode(it.arguments?.getString(ID_FIELD)) ?: BUSINESS_CARD_DEFAULT,
            backToMain = { route -> appState.navigate(route) },
        )
    }
}