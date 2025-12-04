plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.sostenibilidadjavierespino"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.sostenibilidadjavierespino"
        minSdk = 30
        targetSdk = 36
        versionCode = 13
        versionName = "1.13"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // Dependencias AndroidX / Material
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.cronet.embedded)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // ✅ OkHttp para solicitudes HTTP
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
}
