package com.akmj.myapplication.android.di

import com.akmj.myapplication.android.data.storage.DataStoreTokenStorage
import com.akmj.myapplication.domain.storage.TokenStorage
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidModule = module {
    single<TokenStorage> { DataStoreTokenStorage(androidContext()) }
}