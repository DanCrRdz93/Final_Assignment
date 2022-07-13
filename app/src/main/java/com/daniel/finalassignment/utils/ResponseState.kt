package com.daniel.finalassignment.utils

import java.lang.Exception

sealed class ResponseState {
    object LOADING: ResponseState()
    data class SUCCESS<T>(val response: T): ResponseState()
    data class ERROR(val error: Exception): ResponseState()

}