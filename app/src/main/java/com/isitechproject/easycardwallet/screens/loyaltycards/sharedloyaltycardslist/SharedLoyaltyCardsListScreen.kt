package com.isitechproject.easycardwallet.screens.loyaltycards.sharedloyaltycardslist

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.isitechproject.easycardwallet.ui.components.BasicStructure
import com.isitechproject.easycardwallet.ui.components.SharedCard
import com.isitechproject.easycardwallet.ui.components.SharedCardList
import kotlinx.coroutines.awaitAll


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