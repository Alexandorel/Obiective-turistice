plugins {
    alias(libs.plugins.android.application)
    // Compilatorul Compose; suportul Kotlin este inclus in AGP 9.
    alias(libs.plugins.kotlin.compose)
    // @Serializable pentru rutele type-safe.
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "ro.calculmobil.obiective"
    compileSdk = 37

    defaultConfig {
        applicationId = "ro.calculmobil.obiective"
        minSdk = 26
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.navigation.compose)
    debugImplementation(libs.androidx.compose.ui.tooling)
}
