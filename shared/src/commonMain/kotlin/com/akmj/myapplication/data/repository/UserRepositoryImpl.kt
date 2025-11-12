package com.akmj.myapplication.data.repository

import com.akmj.myapplication.data.KtorApiClient
import com.akmj.myapplication.data.LoginRequest
import com.akmj.myapplication.data.LoginResponse
import com.akmj.myapplication.domain.repository.UserRepository
import com.akmj.myapplication.logDebug
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class UserRepositoryImpl: UserRepository {

    private val client = KtorApiClient.client
    private val baseUrl = KtorApiClient.baseUrl
    private val TAG = "UserImpl" // <-- Kembalikan TAG asli


    override suspend fun login(email: String, password: String): String {
        val tag = "$TAG-Login"
        logDebug(tag, "Mencoba login dengan email: $email")

        try {
            // 1. Buat body request-nya
            val requestBody = LoginRequest(email = email, password = password)

            // 2. Tembak request POST ke "/login"
            val response = client.post("$baseUrl/login") {
                // 3. Set header-nya (bilang kita kirim JSON)
                contentType(ContentType.Application.Json)
                // 4. Set body-nya
                setBody(requestBody)
            }

            // 5. Ambil response-nya
            val loginResponse = response.body<LoginResponse>()

            logDebug(tag, "BERHASIL Login. Token didapat.")
            return loginResponse.token

        } catch (e: Exception) {
            // 6. Jika GAGAL (email/pass salah, server mati, dll)
            logDebug(tag, "GAGAL Login: ${e.message}")
            return "" // Kembalikan string kosong jika gagal
        }
    }
}