package com.example.cryptotracker.core.domain.util

import android.content.Context
import com.example.cryptotracker.R


enum class NetworkError : Error {
    NO_INTERNET, TOO_MANY_REQUEST, REQUEST_TIMEOUT, SERVER_ERROR, SERIALIZATION, UNKNOWN
}

fun NetworkError.toString(context: Context): String {
    val error = when (this) {
        NetworkError.NO_INTERNET -> R.string.error_no_internet
        NetworkError.TOO_MANY_REQUEST -> R.string.error_to_many_request
        NetworkError.REQUEST_TIMEOUT -> R.string.error_request_timeout
        NetworkError.SERVER_ERROR -> R.string.error_something_went_wrong
        NetworkError.SERIALIZATION -> R.string.error_serialization
        NetworkError.UNKNOWN -> R.string.error_something_went_wrong
    }
    return context.getString(error)
}
