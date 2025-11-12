package com.akmj.myapplication.presentation

import com.akmj.myapplication.domain.usecase.LoginUseCase
import com.akmj.myapplication.logDebug
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class LoginState{
    data object Idle: LoginState()
    data object Loading: LoginState()
    data object Success: LoginState()
    data class Error(val message: String) : LoginState()
}

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) {
    private val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val TAG = "LoginVM"

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun login(email: String, password: String){
        logDebug(TAG, "login() dipanggil")

        _loginState.value = LoginState.Loading

        viewModelScope.launch {
            try{
                loginUseCase.invoke(email, password)

                logDebug(TAG,"Login Sukses")
                _loginState.value = LoginState.Success
            }catch (e:Exception){
                logDebug(TAG,"Login Gagal")
                _loginState.value = LoginState.Error(e.message ?: "Email dan Password Salah")
            }
        }
    }

    fun onClear(){
        logDebug(TAG,"onClear() dipanggil")
        viewModelScope.cancel()
    }
}