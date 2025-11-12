package com.akmj.myapplication.domain.usecase // <-- sesuaikan package-mu

import com.akmj.myapplication.domain.model.Product
import com.akmj.myapplication.domain.repository.ProductRepository

// Ini adalah 'Use Case' (si spesialis)
// Tugasnya: 1. Ambil produk dari repo, 2. Urutkan A-Z
class GetProductsUseCase(
    private val repository: ProductRepository // Dia "minta" kontrak, bukan implementasi
) {
    suspend fun invoke(): List<Product> {
        // Ini adalah Logika Bisnis-mu
        return repository.getProducts().sortedBy { it.name }
    }
}