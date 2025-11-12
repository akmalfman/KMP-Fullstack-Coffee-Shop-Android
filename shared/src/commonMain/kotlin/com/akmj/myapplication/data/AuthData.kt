package com.akmj.myapplication.data // <-- Sesuaikan package-mu

import kotlinx.serialization.Serializable

// Ini adalah data yang kita KIRIM ke API
@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

// Ini adalah data yang kita TERIMA dari API
@Serializable
data class LoginResponse(
    val token: String
)