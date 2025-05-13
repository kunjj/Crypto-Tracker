package com.example.cryptotracker

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cryptotracker.core.presentation.util.ObserveAsEvents
import com.example.cryptotracker.crpto.presentation.coin_details.CoinDetailsScreen
import com.example.cryptotracker.crpto.presentation.crypto_list.CoinListEvent
import com.example.cryptotracker.crpto.presentation.crypto_list.CoinListScreen
import com.example.cryptotracker.crpto.presentation.viewmodels.CoinListViewModels
import com.example.cryptotracker.ui.theme.CryptoTrackerTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel = koinViewModel<CoinListViewModels>()
                    val state = viewModel.state.collectAsStateWithLifecycle().value

                    ObserveAsEvents(events = viewModel.event) { event ->
                        when (event) {
                            is CoinListEvent.Error -> Toast.makeText(
                                this@MainActivity,
                                event.error.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    when {
                        state.selectedCoin != null -> CoinDetailsScreen(state = state, modifier = Modifier.padding(innerPadding))
                        else -> CoinListScreen(
                            state = state,
                            onCoinClick = viewModel::onAction,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}
