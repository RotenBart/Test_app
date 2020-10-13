package com.example.testapp.viewmodel

sealed class ResultState<out T : Any> {
    object Loading: ResultState<Nothing>()
    data class Success<out T : Any>(val data: T) : ResultState<T>()
    data class Error(val exception: Throwable) : ResultState<Nothing>()
}