package com.isitechproject.easycardwallet.screens.loyaltycards.sharedloyaltycardslist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.isitechproject.easycardwallet.ui.components.BasicStructure
import com.isitechproject.easycardwallet.ui.components.SharedCard
import com.isitechproject.easycardwallet.ui.components.SharedCardList


@Composable
fun SharedLoyaltyCardsListScreen(
    restartApp: (String) -> Unit,
    switchScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SharedLoyaltyCardsListViewModel = hiltViewModel()
) {
    val sharedLoyaltyCards = viewModel.sharedLoyaltyCards.collectAsState(emptyList())
    val loyaltyCards = viewModel.loyaltyCards.collectAsState(emptyList())

    viewModel.initializeData()

    BasicStructure(
        restartApp = restartApp,
        switchScreen = switchScreen,
        viewModel = viewModel,
        modifier = modifier,
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            SharedCardList(
                sharedCardsList = sharedLoyaltyCards.value,
                cardsList = loyaltyCards.value,
                getUserEmail = { viewModel.getUserEmail(it) }
            ) {
                viewModel.stopSharing(it)
            }
            LazyColumn(
                contentPadding = PaddingValues(vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(sharedLoyaltyCards.value) { sharedLoyaltyCard ->
                    SharedCard(
                        cardName = loyaltyCards.value.first { it.id == sharedLoyaltyCard.sharedCardId }.name,
                        userEmail = viewModel.getUserEmail(sharedLoyaltyCard.sharedUid)) {
                        viewModel.stopSharing(sharedLoyaltyCard.id)
                    }
                }
            }
        }
    }
}