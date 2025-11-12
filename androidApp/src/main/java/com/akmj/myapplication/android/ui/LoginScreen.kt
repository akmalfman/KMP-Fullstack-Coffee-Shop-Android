package com.akmj.myapplication.android.ui // <-- Sesuaikan package-mu

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.akmj.myapplication.presentation.LoginViewModel
import com.akmj.myapplication.presentation.LoginState
import org.koin.compose.koinInject

@Composable
fun LoginScreen(
    // 'onLoginSuccess' adalah "sinyal" yang kita kirim
    // kembali ke 'App' jika login berhasil
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = koinInject()
) {
    // Ambil 'state' (Idle, Loading, Success, Error) dari ViewModel
    val state by viewModel.loginState.collectAsStateWithLifecycle()

    // State lokal untuk 'email' and 'password'
    var email by remember { mutableStateOf("root@gmail.com") } // (Debug, ganti/hapus nanti)
    var password by remember { mutableStateOf("rootroot") } // (Debug, ganti/hapus nanti)

    // "Dengarkan" state. Jika 'Success', panggil 'onLoginSuccess'
    LaunchedEffect(state) {
        if (state is LoginState.Success) {
            onLoginSuccess()
        }
    }

    // "Dengarkan" lifecycle
    DisposableEffect(Unit) {
        onDispose {
            viewModel.onClear()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Login", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Tampilkan tombol atau 'loading spinner'
        when (state) {
            is LoginState.Loading -> {
                CircularProgressIndicator()
            }
            else -> {
                Button(
                    onClick = { viewModel.login(email, password) },
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text("LOGIN")
                }
            }
        }

        // Tampilkan pesan error jika ada
        if (state is LoginState.Error) {
            Text(
                text = (state as LoginState.Error).message,
                color = Color.Red,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}