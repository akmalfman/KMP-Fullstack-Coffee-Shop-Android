package com.akmj.myapplication

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android // Engine Ktor khusus Android

class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()


// "Kenyataan" (Actual) untuk 'getKtorEngine'
actual fun getKtorEngine(): HttpClientEngine {
    return Android.create()
}

// "Kenyataan" (Actual) untuk 'getBaseUrl'
// Ini adalah IP "ajaib" emulator untuk 'localhost'
// Sesuaikan dengan  IPv4 Address device mu (laptop)
actual fun getBaseUrl(): String {
    return "http://10.186.26.43:8080"
}

actual fun logDebug(tag: String, message: String) {
    println("[$tag]: $message")
}