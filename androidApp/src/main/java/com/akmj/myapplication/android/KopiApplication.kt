package com.akmj.myapplication.android // <-- sesuaikan package-mu

import android.app.Application
import com.akmj.myapplication.android.di.androidModule
import com.akmj.myapplication.di.appModule // <-- Import "buku resep" kita
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class KopiApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // "Nyalakan" Koin
        startKoin {
            androidLogger() // Logger Koin untuk Android
            androidContext(this@KopiApplication) // Beri tahu Koin soal 'context'
            modules(appModule, androidModule) // Beri tahu Koin "resep" mana yang dipakai
        }
    }
}