package com.isitechproject.easycardwallet.screens.visitcards.sharedvisitcardslist

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
fun SharedVisitCardsListScreen(
    restartApp: (String) -> Unit,
    switchScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SharedVisitCardsListViewModel = hiltViewModel()
) {
    val sharedVisitCards = viewModel.sharedVisitCards.collectAsState(emptyList())
    val visitCards = viewModel.visitCards.collectAsState(emptyList())

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
                sharedCardsList = sharedVisitCards.value,
                cardsList = visitCards.value,
                getUserEmail = { viewModel.getUserEmail(it) }
            ) {
                viewModel.stopSharing(it)
            }
            LazyColumn(
                contentPadding = PaddingValues(vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(sharedVisitCards.value) { sharedLoyaltyCard ->
                    SharedCard(
                        cardName = visitCards.value.first { it.id == sharedLoyaltyCard.sharedCardId }.name,
                        userEmail = viewModel.getUserEmail(sharedLoyaltyCard.sharedUid)) {
                        viewModel.stopSharing(sharedLoyaltyCard.id)
                    }
                }
            }
        }
    }
}