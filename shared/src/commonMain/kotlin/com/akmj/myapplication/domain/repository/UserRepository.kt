package com.akmj.myapplication.domain.repository

interface UserRepository {
    suspend fun login(email: String, password: String): String
}