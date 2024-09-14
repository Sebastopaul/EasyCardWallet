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
        arguments = listOf(
            navArgument(BusinessCard.COMPANY_NAME_FIELD) { defaultValue = BUSINESS_CARD_DEFAULT },
            navArgument(BusinessCard.ADDRESS_FIELD) { defaultValue = BUSINESS_CARD_DEFAULT },
            navArgument(BusinessCard.ZIP_FIELD) { defaultValue = BUSINESS_CARD_DEFAULT },
            navArgument(BusinessCard.CITY_FIELD) { defaultValue = BUSINESS_CARD_DEFAULT },
            navArgument(BusinessCard.CONTACT_FIRSTNAME_FIELD) { defaultValue = BUSINESS_CARD_DEFAULT },
            navArgument(BusinessCard.CONTACT_LASTNAME_FIELD) { defaultValue = BUSINESS_CARD_DEFAULT },
            navArgument(BusinessCard.CONTACT_PHONE_FIELD) { defaultValue = BUSINESS_CARD_DEFAULT },
            navArgument(BusinessCard.CONTACT_MOBILE_FIELD) { defaultValue = BUSINESS_CARD_DEFAULT },
            navArgument(BusinessCard.CONTACT_EMAIL_FIELD) { defaultValue = BUSINESS_CARD_DEFAULT },
        ),
    ) {
        val context = LocalContext.current as BusinessCardScannerActivity
        context.shutdownCamera()

        CreateBusinessCardScreen(
            businessCard = BusinessCard(
                companyName = Uri.decode(it.arguments?.getString(BusinessCard.COMPANY_NAME_FIELD)) ?: BUSINESS_CARD_DEFAULT,
                address = Uri.decode(it.arguments?.getString(BusinessCard.ADDRESS_FIELD)) ?: BUSINESS_CARD_DEFAULT,
                zip = Uri.decode(it.arguments?.getString(BusinessCard.ZIP_FIELD)) ?: BUSINESS_CARD_DEFAULT,
                city = Uri.decode(it.arguments?.getString(BusinessCard.CITY_FIELD)) ?: BUSINESS_CARD_DEFAULT,
                contactFirstname = Uri.decode(it.arguments?.getString(BusinessCard.CONTACT_FIRSTNAME_FIELD)) ?: BUSINESS_CARD_DEFAULT,
                contactLastname = Uri.decode(it.arguments?.getString(BusinessCard.CONTACT_LASTNAME_FIELD)) ?: BUSINESS_CARD_DEFAULT,
                contactPhone = Uri.decode(it.arguments?.getString(BusinessCard.CONTACT_PHONE_FIELD)) ?: BUSINESS_CARD_DEFAULT,
                contactMobile = Uri.decode(it.arguments?.getString(BusinessCard.CONTACT_MOBILE_FIELD)) ?: BUSINESS_CARD_DEFAULT,
                contactEmail = Uri.decode(it.arguments?.getString(BusinessCard.CONTACT_EMAIL_FIELD)) ?: BUSINESS_CARD_DEFAULT,
            ),
            backToMain = { route -> appState.navigate(route) },
        )
    }
}