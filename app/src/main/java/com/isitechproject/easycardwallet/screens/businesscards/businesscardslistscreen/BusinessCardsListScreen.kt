package com.isitechproject.easycardwallet.screens.businesscards.businesscardslistscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.isitechproject.easycardwallet.R
import com.isitechproject.easycardwallet.ui.components.BasicStructure
import com.isitechproject.easycardwallet.ui.components.CardList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessCardsListScreen(
    restartApp: (String) -> Unit,
    switchScreen: (String) -> Unit,
    openScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BusinessCardsListViewModel = hiltViewModel()
) {
    BasicStructure(
        restartApp = restartApp,
        switchScreen = switchScreen,
        viewModel = viewModel,
        modifier = modifier,
    ) {
        val userBusinessCards by viewModel.userBusinessCards.collectAsState(emptyList())
        val sharedBusinessCards by viewModel.sharedBusinessCards.collectAsState(emptyList())

        Column {
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
            ) {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.business_card_list)) },
                    actions = {
                        IconButton(onClick = { viewModel.onAddClick(openScreen) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.add_card_24),
                                contentDescription = stringResource(R.string.add_card),
                            )
                        }
                        IconButton(onClick = { viewModel.onSharedClick(openScreen) }) {
                            Icon(
                                imageVector = Icons.Filled.Share,
                                contentDescription = stringResource(R.string.shared_business_cards),
                            )
                        }
                    }
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
            )

            CardList(
                cards = userBusinessCards + sharedBusinessCards,
                onCardClick = { viewModel.onBusinessCardClick(openScreen, it.id) },
            )
        }
    }
}
