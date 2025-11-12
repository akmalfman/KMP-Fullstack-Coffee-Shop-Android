package com.akmj.myapplication.data.repository

import com.akmj.myapplication.data.KtorApiClient
import com.akmj.myapplication.data.ProductDTO
import com.akmj.myapplication.domain.model.Product
import com.akmj.myapplication.domain.repository.ProductRepository
import com.akmj.myapplication.logDebug
import io.ktor.client.call.body
import io.ktor.client.request.get

class ProductRepositoryImpl : ProductRepository {

    private val client = KtorApiClient.client
    private val baseUrl = KtorApiClient.baseUrl
    private val TAG = "RepoImpl" // <-- Kembalikan TAG asli

    override suspend fun getProducts(): List<Product> {
        logDebug(TAG, "Mencoba fetch dari: $baseUrl/products") // <-- Kembali ke /products
        try {
            // Tembak request GET ke '/products'
            val response = client.get("$baseUrl/products")

            // Ambil DTO (model kotor)
            val productDtos = response.body<List<ProductDTO>>()

            logDebug(TAG, "BERHASIL: dapat ${productDtos.size} produk DTO")

            // "TERJEMAHKAN" dari DTO ke Model Bersih
            return productDtos.map { dto ->
                Product(
                    id = dto.id,
                    name = dto.name,
                    price = dto.price
                )
            }

        } catch (e: Exception) {
            logDebug(TAG, "GAGAL fetch: ${e.message}")
            return emptyList() // Kembalikan list kosong jika gagal
        }
    }
}