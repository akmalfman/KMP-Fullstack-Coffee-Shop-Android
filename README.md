# KMP Full-Stack Coffee Shop (Android)

<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin">
  <img src="https://img.shields.io/badge/Jetpack_Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white" alt="Jetpack Compose">
  <img src="https://img.shields.io/badge/Ktor-0095D5?style=for-the-badge&logo=ktor&logoColor=white" alt="Ktor">
  <img src="https://img.shields.io/badge/Koin-00B2FF?style=for-the-badge&logo=koin&logoColor=white" alt="Koin">
</p>

Ini adalah proyek **Native Android** yang dibangun menggunakan **Kotlin Multiplatform (KMP)**. Aplikasi ini berfungsi sebagai "klien" *mobile* untuk [API Backend Go (Go-Kopi API)](https://github.com/akmalfman/go-clean-architecture-api).

Proyek ini adalah studi kasus mendalam tentang implementasi **Clean Architecture** di lingkungan KMP. **Seluruh logika bisnis**—termasuk *Networking*, *Repository*, *Use Case*, dan *ViewModel*—hidup di dalam modul `commonMain` (dibagikan), sementara UI-nya 100% *native* **Jetpack Compose** di `androidMain`.

---

## 🚀 Fitur Utama

* **Clean Architecture (Shared):** Arsitektur proyek dibagi secara bersih menjadi 3 lapisan (`Data`, `Domain`, `Presentation`) di dalam `commonMain`.
* **Networking (Ktor):** Menggunakan **Ktor Client** di `commonMain` untuk mengonsumsi API Go (termasuk `GET /products` dan `POST /login`).
* **Dependency Injection (Koin):** Menggunakan **Koin** untuk meng-inject semua *dependency* secara bersih di seluruh modul (`Repository` -> `UseCase` -> `ViewModel`).
* **Reactive UI (Compose):** 100% UI *native* dibangun dengan **Jetpack Compose**, meng-observasi `StateFlow` dari `commonMain` ViewModel.
* **Authentication Flow:** Alur login penuh. Berhasil login akan meng-generate **JWT Token** (dari Go API), yang kemudian disimpan di Android.
* **Secure Storage (expect/actual):**
    * `commonMain` "menjanjikan" (`expect`) sebuah `TokenStorage` (brankas token).
    * `androidMain` "menepati janji" (`actual`) dengan implementasi **Jetpack DataStore** untuk menyimpan JWT dengan aman.
* **Dynamic UI "Router":** Aplikasi secara reaktif "memilih" halaman mana yang akan ditampilkan (`LoginScreen` vs. `ProductScreen`) dengan meng-observasi `token` di `TokenStorage`.

---

## 🛠️ Tumpukan Teknologi (Tech Stack)

* **Framework:** Kotlin Multiplatform (KMP)
* **Bahasa:** 100% Kotlin
* **Arsitektur:** Clean Architecture (MVVM di *Presentation Layer*)
* **UI (Android):** Jetpack Compose
* **Dependency Injection:** Koin
* **Networking (`commonMain`):** Ktor Client
* **Concurrency (`commonMain`):** Kotlin Coroutines (`StateFlow`, `viewModelScope`)
* **Storage (`androidMain`):** Jetpack DataStore (via `expect/actual`)

---

## 🏁 Cara Menjalankan (Getting Started)

### Prasyarat

1.  **Backend API (WAJIB):** [Go-Kopi API](https://github.com/akmalfman/go-clean-architecture-api) **harus** sudah berjalan (`docker-compose up`) di `localhost:8080`.
2.  **Android Studio:** Versi Jellyfish atau lebih baru.
3.  **Plugin:** Plugin "Kotlin Multiplatform Mobile" harus ter-install di Android Studio.

### Instalasi

1.  **Clone repositori ini:**
    ```bash
    git clone https://github.com/akmalfman/KMP-Fullstack-Coffee-Shop-Android.git
    ```
2.  **Buka proyek**
    Buka proyek ini di Android Studio.
3.  **PENTING (Koneksi Emulator):**
    Aplikasi ini dikonfigurasi untuk "berbicara" ke `http://10.0.2.2:8080` (IP "laptop" Emulator Android untuk `localhost`). Pastikan *backend* Go-mu berjalan di `localhost:8080`.
4.  **Sync Gradle:**
    Tunggu Gradle selesai men-download semua dependensi KMP, Ktor, dan Koin.
5.  **Jalankan Aplikasi:**
    Pilih target `androidApp` dan tekan **Run (▶️)**.

---

## 📁 Arsitektur `commonMain` (Shared Code)

* **`data` (Data Layer):**
    * `KtorApiClient.kt`: Konfigurasi Ktor Client.
    * `ProductDTO.kt` / `AuthData.kt`: Model data (DTO) dari API.
    * `repository/ProductRepositoryImpl.kt`: Implementasi "Kenyataan" (cara *fetch* data pakai Ktor).
* **`domain` (Domain Layer):**
    * `model/Product.kt`: Model data "bersih" yang dipakai oleh UI.
    * `repository/ProductRepository.kt`: *Interface* "Janji" (kontrak untuk data).
    * `storage/TokenStorage.kt`: *Interface* "Janji" (`expect`) untuk brankas token.
    * `usecase/GetProductsUseCase.kt` / `LoginUseCase.kt`: "Spesialis" logika bisnis.
* **`presentation` (Presentation Layer):**
    * `HomeViewModel.kt` / `LoginViewModel.kt`: "Otak" UI yang 100% *shared*.
* **`di` (Dependency Injection):**
    * `AppModule.kt`: "Buku resep" Koin untuk semua *dependency* di atas.
