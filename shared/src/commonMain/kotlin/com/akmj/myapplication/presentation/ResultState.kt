package com.akmj.myapplication.presentation // <-- Sesuaikan package-mu

// Ini adalah 'Sealed Class'. Anggap saja 'enum' yang canggih.
// Dia bilang: "Sebuah ResultState HANYA bisa jadi 3 hal ini:"
sealed class ResultState<out T> {
    // 1. Sedang Loading
    data object Loading : ResultState<Nothing>()

    // 2. Sukses (dan membawa 'data')
    data class Success<T>(val data: T) : ResultState<T>()

    // 3. Gagal (dan membawa 'message' error)
    data class Error(val message: String) : ResultState<Nothing>()
}