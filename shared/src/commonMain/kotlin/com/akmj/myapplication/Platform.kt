package com.akmj.myapplication

import io.ktor.client.engine.HttpClientEngine

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

// "Janji" #1: Saya harap (expect) tiap platform (Android, iOS)
// akan memberi saya 'engine' HTTP-nya masing-masing.
expect fun getKtorEngine(): HttpClientEngine

// "Janji" #2: Saya harap tiap platform
// akan memberi saya 'baseUrl'-nya masing-masing.
expect fun getBaseUrl(): String

expect fun logDebug(tag: String, message: String)