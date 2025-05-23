package com.example.cryptotracker.core.domain.navigation

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cryptotracker.core.presentation.util.ObserveAsEvents
import com.example.cryptotracker.crpto.presentation.coin_details.CoinDetailsScreen
import com.example.cryptotracker.crpto.presentation.crypto_list.CoinListEvent
import com.example.cryptotracker.crpto.presentation.crypto_list.CoinListScreen
import com.example.cryptotracker.crpto.presentation.models.CoinListAction
import com.example.cryptotracker.crpto.presentation.viewmodels.CoinListViewModels
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptiveCoinListDetailPane(
    modifier: Modifier = Modifier,
    viewModel: CoinListViewModels = koinViewModel<CoinListViewModels>(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    val context = LocalContext.current
    ObserveAsEvents(events = viewModel.event) { event ->
        when (event) {
            is CoinListEvent.Error -> Toast.makeText(
                context, event.error.toString(), Toast.LENGTH_LONG
            ).show()
        }
    }

    val scope = rememberCoroutineScope()

    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    NavigableListDetailPaneScaffold(navigator = navigator, modifier = modifier, listPane = {
        AnimatedPane {
            CoinListScreen(state = state, onCoinClick = { action ->
                viewModel.onAction(action)
                when (action) {
                    is CoinListAction.OnCoinClick -> scope.launch {
                        navigator.navigateTo(pane = ListDetailPaneScaffoldRole.Detail)
                    }
                    is CoinListAction.OnSwipeRefresh -> {}
                }
            })
        }
    }, detailPane = { AnimatedPane { CoinDetailsScreen(state = state) } })
}
