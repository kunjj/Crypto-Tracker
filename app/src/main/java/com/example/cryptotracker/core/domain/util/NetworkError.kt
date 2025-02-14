package com.example.cryptotracker.core.domain.util

enum class NetworkError : Error {
    NO_INTERNET, TOO_MANY_REQUEST, REQUEST_TIMEOUT, SERVER_ERROR, SERIALIZATION, UNKNOWN
}
