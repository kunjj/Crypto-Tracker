package com.example.cryptotracker.di

import com.example.cryptotracker.core.data.HttpClientFactory
import com.example.cryptotracker.crpto.data.networking.RemoteCoinDataSource
import com.example.cryptotracker.crpto.domain.CoinDataSource
import com.example.cryptotracker.crpto.presentation.viewmodels.CoinListViewModels
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single {
        HttpClientFactory.create(CIO.create())
    }

    single {
        RemoteCoinDataSource(get())
    }.bind<CoinDataSource>()

    // singleOf(::RemoteCoinDataSource).bind<CoinDataSource>() short hand of writing when koin knows where to get the instance

    viewModelOf(::CoinListViewModels)
}
