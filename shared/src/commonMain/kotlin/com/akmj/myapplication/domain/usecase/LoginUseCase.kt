package com.akmj.myapplication.domain.usecase

import com.akmj.myapplication.domain.repository.UserRepository
import com.akmj.myapplication.domain.storage.TokenStorage

class LoginUseCase(
    private val userRepository: UserRepository,
    private val tokenStorage: TokenStorage
) {
    suspend fun invoke(email: String, password: String){
        val token = userRepository.login(email,password)

        if (token.isNotBlank()){
            tokenStorage.saveToken(token)
        }else{
            throw Exception("Login gagal: Token tidak diterima")
        }
    }
}