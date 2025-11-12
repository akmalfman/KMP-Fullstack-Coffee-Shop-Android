package com.akmj.myapplication.presentation

import com.akmj.myapplication.domain.model.Product
import com.akmj.myapplication.domain.usecase.GetProductsUseCase
import com.akmj.myapplication.logDebug
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getProductsUseCase: GetProductsUseCase
) {
    private val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val TAG = "HomeVM"

    // --- KEMBALIKAN KE STATE SEMULA ---
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()
    // ---------------------------------

    fun loadProducts() {
        logDebug(TAG, "loadProducts() dipanggil...")
        viewModelScope.launch {
            // Panggil Use Case (yang akan 'catch' error-nya)
            val productList = getProductsUseCase.invoke()
            _products.value = productList
            logDebug(TAG, "Selesai load. State di-update dengan ${productList.size} produk.")
        }
    }

    fun onClear() {
        logDebug(TAG, "onClear() dipanggil.")
        viewModelScope.cancel()
    }
}