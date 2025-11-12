package com.akmj.myapplication.android // <-- Sesuaikan package-mu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.akmj.myapplication.domain.storage.TokenStorage
import com.akmj.myapplication.android.ui.LoginScreen
import com.akmj.myapplication.android.ui.ProductScreen
import org.koin.compose.koinInject

@Composable
fun App() {
    // 1. "Suntik" brankas-nya (TokenStorage)
    val tokenStorage: TokenStorage = koinInject()

    // 2. "Amati" token-nya.
    //    'collectAsStateWithLifecycle(null)' artinya nilai awalnya 'null'
    val token by tokenStorage.getToken().collectAsStateWithLifecycle(initialValue = null)

    // 3. Logika "Satpam"
    //    'isLoggedIn' akan 'true' jika token-nya tidak 'null' DAN tidak 'blank'
    val isLoggedIn = !token.isNullOrBlank()

    if (isLoggedIn) {
        // Jika SUDAH login, tampilkan layar Produk
        ProductScreen()
    } else {
        // Jika BELUM login, tampilkan layar Login
        // Kita oper 'dummy function' untuk 'onLoginSuccess'
        // (karena 'token' akan otomatis update & 'recompose' halaman ini)
        LoginScreen(onLoginSuccess = {
            // Tidak perlu melakukan apa-apa,
            // 'token' akan update & 'if' ini akan jalan ulang
        })
    }
}