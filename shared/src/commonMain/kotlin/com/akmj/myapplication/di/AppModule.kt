package com.akmj.myapplication.di // <-- sesuaikan package-mu

import com.akmj.myapplication.data.repository.ProductRepositoryImpl
import com.akmj.myapplication.data.repository.UserRepositoryImpl
import com.akmj.myapplication.domain.repository.ProductRepository
import com.akmj.myapplication.domain.repository.UserRepository
import com.akmj.myapplication.domain.usecase.GetProductsUseCase
import com.akmj.myapplication.domain.usecase.LoginUseCase
import com.akmj.myapplication.presentation.HomeViewModel
import com.akmj.myapplication.presentation.LoginViewModel
import org.koin.dsl.module

// Ini adalah "buku resep" untuk Koin
val appModule = module {

    // "Resep #1: Jika ada yang minta 'ProductRepository' (Interface)..."
    // "...beri dia 'ProductRepositoryImpl' (Implementasi)."
    // 'single' artinya "bikin 1 kali aja, pakai terus" (singleton)
    single<ProductRepository> { ProductRepositoryImpl() }

    // "Resep #2: Jika ada yang minta 'GetProductsUseCase'..."
    // "...beri dia 'GetProductsUseCase'. Koin otomatis akan
    // mengambil 'ProductRepository' dari resep #1 untuk 'UseCase' ini."
    single { GetProductsUseCase(get()) }

    // Nanti kita tambahkan resep 'ViewModel' di sini
    // 2. TAMBAHKAN RESEP INI
    // 'factory' = "Beri aku instance BARU setiap kali diminta"
    // Ini penting untuk ViewModel
    factory { HomeViewModel(get()) }

    single<UserRepository> { UserRepositoryImpl() }

    factory { LoginUseCase(get(), get()) }

    factory { LoginViewModel(get()) }
}