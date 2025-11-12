package com.akmj.myapplication.domain.repository

import com.akmj.myapplication.domain.model.Product

// Ini adalah "Kontrak"
interface ProductRepository {
    // "Saya tidak peduli caranya, pokoknya
    // beri saya List<Product> (model bersih)"
    suspend fun getProducts(): List<Product>
}