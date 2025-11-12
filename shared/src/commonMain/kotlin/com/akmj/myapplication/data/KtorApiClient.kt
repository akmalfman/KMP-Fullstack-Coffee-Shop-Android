package com.akmj.myapplication.data

import com.akmj.myapplication.getBaseUrl
import com.akmj.myapplication.getKtorEngine
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

// Kita buat 'object' (singleton) agar hanya ada 1 Ktor Client
object KtorApiClient {

    // 1. Buat Ktor Client-nya
    val client = HttpClient(getKtorEngine()) { // Pakai "engine" dari 'actual'

        // 2. Pasang plugin ContentNegotiation (untuk JSON)
        install(ContentNegotiation) {
            json(Json {
                // Konfigurasi JSON:
                prettyPrint = true      // (Bagus untuk debug)
                isLenient = true        // (Toleran terhadap JSON)
                ignoreUnknownKeys = true // (WAJIB: Abaikan field yg tidak ada di data class)
            })
        }
    }

    // 3. Kita simpan 'baseUrl' dari 'actual'
    val baseUrl = getBaseUrl()
}