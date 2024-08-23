package com.isitechproject.easycardwallet

import android.util.Log
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
import androidx.navigation.navArgument
import com.isitechproject.easycardwallet.screens.auth.sign_in.SignInScreen
import com.isitechproject.easycardwallet.screens.auth.sign_up.SignUpScreen
import com.isitechproject.barcodescanner.BarcodeScannerActivity
import com.isitechproject.barcodescanner.CREATE_LOYALTY_CARD_SCREEN
import com.isitechproject.easycardwallet.screens.loyaltycards.loyaltycardscreen.LoyaltyCardScreen
import com.isitechproject.easycardwallet.screens.loyaltycards.loyaltycardslistscreen.LoyaltyCardsListScreen
import com.isitechproject.easycardwallet.screens.loyaltycards.sharedloyaltycardslist.SharedLoyaltyCardsListScreen
import com.isitechproject.easycardwallet.screens.splash.SplashScreen
import com.isitechproject.easycardwallet.screens.visitcards.sharedvisitcardslist.SharedVisitCardsListScreen
import com.isitechproject.easycardwallet.screens.visitcards.visitcardscreen.VisitCardScreen
import com.isitechproject.easycardwallet.screens.visitcards.visitcardslistscreen.VisitCardsListScreen
import com.isitechproject.easycardwallet.ui.theme.EasyCardWalletTheme

@Composable
fun EasyCardWalletApp() {
    EasyCardWalletTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val appState = rememberAppState()

            Scaffold { innerPaddingModifier ->
                NavHost(
                    navController = appState.navController,
                    startDestination = SPLASH_SCREEN,
                    modifier = Modifier.padding(innerPaddingModifier)
                ) {
                    easyCardWalletGraph(appState)
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

fun NavGraphBuilder.easyCardWalletGraph(appState: EasyCardWalletAppState) {
    activity(SCAN_LOYALTY_CARD_SCREEN) {
        activityClass = BarcodeScannerActivity::class
    }

    composable(SHARED_LOYALTY_CARDS_SCREEN) {
        SharedLoyaltyCardsListScreen(
            restartApp = { route -> appState.clearAndNavigate(route) },
            switchScreen = { route -> appState.navigate(route) }
        )
    }

    composable(
        route = "$LOYALTY_CARD_SCREEN$LOYALTY_CARD_ID_ARG",
        arguments = listOf(navArgument(LOYALTY_CARD_ID) { defaultValue = LOYALTY_CARD_DEFAULT_ID })
    ) {
        LoyaltyCardScreen(
            loyaltyCardId = it.arguments?.getString(LOYALTY_CARD_ID) ?: LOYALTY_CARD_DEFAULT_ID,
            popUpScreen = { appState.popUp() },
            restartApp = { route -> appState.clearAndNavigate(route) },
            switchScreen = { route -> appState.navigate(route) }
        )
    }

    composable(LOYALTY_CARDS_LIST_SCREEN) {
        LoyaltyCardsListScreen(
            restartApp = { route -> appState.clearAndNavigate(route) },
            openScreen = { route -> appState.navigate(route) },
            switchScreen = { route -> if (route != LOYALTY_CARDS_LIST_SCREEN) appState.navigate(route) }
        )
    }

    activity(SCAN_VISIT_CARD_SCREEN) {
        activityClass = BarcodeScannerActivity::class
    }

    composable(SHARED_VISIT_CARDS_SCREEN) {
        SharedVisitCardsListScreen(
            restartApp = { route -> appState.clearAndNavigate(route) },
            switchScreen = { route -> appState.navigate(route) }
        )
    }

    composable(
        route = "$VISIT_CARD_SCREEN$VISIT_CARD_ID_ARG",
        arguments = listOf(navArgument(VISIT_CARD_ID) { defaultValue = VISIT_CARD_DEFAULT_ID })
    ) {
        VisitCardScreen(
            visitCardId = it.arguments?.getString(VISIT_CARD_ID) ?: VISIT_CARD_DEFAULT_ID,
            popUpScreen = { appState.popUp() },
            restartApp = { route -> appState.clearAndNavigate(route) },
            switchScreen = { route -> appState.navigate(route) }
        )
    }

    composable(VISIT_CARDS_LIST_SCREEN) {
        VisitCardsListScreen(
            restartApp = { route -> appState.clearAndNavigate(route) },
            openScreen = { route -> appState.navigate(route) },
            switchScreen = { route -> if (route != VISIT_CARDS_LIST_SCREEN) appState.navigate(route) }
        )
    }

    composable(SIGN_IN_SCREEN) {
        SignInScreen(openAndPopUp = { route, popUp ->
            appState.navigateAndPopUp(
                route,
                popUp
            )
        })
    }

    composable(SIGN_UP_SCREEN) {
        SignUpScreen(openAndPopUp = { route, popUp ->
            appState.navigateAndPopUp(
                route,
                popUp
            )
        })
    }

    composable(SPLASH_SCREEN) {
        SplashScreen(openAndPopUp = { route, popUp ->
            appState.navigateAndPopUp(
                route,
                popUp
            )
        })
    }
}