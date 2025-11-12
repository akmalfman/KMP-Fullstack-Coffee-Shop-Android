package com.akmj.myapplication.android.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.akmj.myapplication.presentation.HomeViewModel
import org.koin.compose.koinInject

@Composable
fun ProductScreen(
    // 1. "Suntik" ViewModel-nya pakai Koin
    //    Koin akan otomatis 'factory'-kan viewmodel-nya
    viewModel: HomeViewModel = koinInject()
) {

    // 2. "Amati" state 'products' dengan aman
//    val state by viewModel.productsState.collectAsStateWithLifecycle()
    val products by viewModel.products.collectAsStateWithLifecycle()

    val isLoading = products.isEmpty()
    // 3. Panggil 'loadProducts()' sekali saja saat Composable pertama kali muncul
    LaunchedEffect(Unit) {
        viewModel.loadProducts()
    }

    // 4. "Dengarkan" lifecycle. Saat UI "mati", panggil 'onClear'
    DisposableEffect(LocalLifecycleOwner.current) {
        onDispose {
            viewModel.onClear()
        }
    }

    // 5. Tampilkan UI-nya
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Kopi Akmal (KMP)",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // --- INI PERUBAHAN BESARNYA ---
        // Kita gunakan 'when' untuk cek 'state'
//        when (val currentState = state) {
//
//            // 1. SAAT LOADING
//            is ResultState.Loading -> {
//                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                    CircularProgressIndicator()
//                }
//            }
//
//            // 2. SAAT GAGAL
//            is ResultState.Error -> {
//                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                    // TAMPILKAN ERRORNYA DI LAYAR!
//                    Text(
//                        text = "GAGAL MEMUAT DATA:\n${currentState.message}",
//                        color = Color.Red,
//                        textAlign = TextAlign.Center
//                    )
//                }
//            }
//
//            // 3. SAAT SUKSES
//            is ResultState.Success -> {
//                // Ambil data dari 'currentState.data'
//                val products = currentState.data
//
//                if (products.isEmpty()) {
//                    Text("Belum ada produk dari API.")
//                } else {
//                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
//                        items(products) { product ->
//                            ProductItem(product = product)
//                            Divider(color = Color.Gray.copy(alpha = 0.3f))
//                        }
//                    }
//                }
//            }
//        }

//        // Tampilkan loading jika 'products' masih kosong
//        if (products.isEmpty()) {
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                CircularProgressIndicator()
//            }
//        } else {
//            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
//                items(products) { product ->
//                    ProductItem(product = product)
//                    Divider(color = Color.Gray.copy(alpha = 0.3f))
//                }
//            }
//        }

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                // Tampilkan loading HANYA jika list-nya kosong
                // Kita akan lihat 'Loading' sesaat, lalu 'Belum ada produk'
                // jika API-nya memang mengembalikan '[]'
                CircularProgressIndicator()
            }
        }

        if (!isLoading && products.isEmpty()) {
            Text("Belum ada produk dari API.")
        } else if (products.isNotEmpty()) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(products) { product ->
                    ProductItem(product = product)
                    Divider(color = Color.Gray.copy(alpha = 0.3f))
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: com.akmj.myapplication.domain.model.Product) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = product.name,
            fontSize = 18.sp
        )
        Text(
            text = "Rp ${product.price}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}