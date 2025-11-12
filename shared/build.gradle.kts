plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
//    listOf(
//        iosX64(),
//        iosArm64(),
//        iosSimulatorArm64()
//    ).forEach {
//        it.binaries.framework {
//            baseName = "shared"
//            isStatic = true
//        }
//    }

    sourceSets {
        // --- INI BAGIAN YANG DITAMBAHKAN (commonMain) ---
        commonMain.dependencies {
            // Ktor Core (Inti)
            implementation(libs.ktor.client.core)
            // Ktor untuk JSON (Content Negotiation)
            implementation(libs.ktor.client.contentnegotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.koin.core)
            implementation(libs.kotlinx.coroutines.core)
        }

        // --- INI BAGIAN YANG DITAMBAHKAN (androidMain) ---
        val androidMain by getting {
            dependencies {
                // Ktor Engine (Mesin HTTP) khusus untuk Android
                implementation(libs.ktor.client.android)
                implementation(libs.koin.android)
                implementation(libs.koin.androidx.compose)
                implementation(libs.androidx.lifecycle.runtime.compose)
            }
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.akmj.myapplication"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
