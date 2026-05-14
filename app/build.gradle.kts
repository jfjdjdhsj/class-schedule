import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

val signingProperties = Properties().apply {
    val signingFile = rootProject.file("key.properties")
    if (signingFile.exists()) {
        signingFile.inputStream().use { load(it) }
    }
}

android {
    namespace = "com.classschedule"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.classschedule"
        minSdk = 26
        targetSdk = 36
        versionCode = 3
        versionName = "1.0.2"
    }

    signingConfigs {
        create("release") {
            if (signingProperties.isNotEmpty()) {
                val storeFilePath = signingProperties.getProperty("storeFile")
                storeFile = storeFilePath?.let { rootProject.file(it) }
                storePassword = signingProperties.getProperty("storePassword")
                keyAlias = signingProperties.getProperty("keyAlias")
                keyPassword = signingProperties.getProperty("keyPassword")
                storeType = signingProperties.getProperty("storeType", "PKCS12")
            }
        }
    }

    buildTypes {
        release {
            if (signingProperties.isNotEmpty()) {
                signingConfig = signingConfigs.getByName("release")
            }
            isMinifyEnabled = true
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

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.work.runtime.ktx)
    debugImplementation(libs.androidx.ui.tooling)
}
