package com.example.mybooks.util

sealed class UiState<T> {
    class Loading<T> : UiState<T>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Failed<T>(val message: String) : UiState<T>()
}


//sealed class UiState<T> {
//    class Loading<T> : UiState<T>()
//    data class Success<T>(val data: T) : UiState<T>()
//    data class Failed<T>(val message: String) : UiState<T>()
//}
