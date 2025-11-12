package com.akmj.myapplication.data

import kotlinx.serialization.Serializable

@Serializable
data class ProductDTO(
    val id: Int,
    val name: String,
    val price: Int
)