plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp) // Add KSP plugin for Room
//    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.ajidroid.hissab"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.ajidroid.hissab"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

configurations.all {
    resolutionStrategy {
        force(libs.jetbrains.annotations.get().toString())
    }
}

dependencies {
    implementation(libs.jetbrains.annotations) {
        exclude(group = "com.intellij", module = "annotations")
    }

    implementation(libs.navigation.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

//    implementation("androidx.navigation:navigation-compose:2.7.5")

//    implementation(libs.androidx.room.runtime)
//    implementation(libs.androidx.room.compiler)
//    implementation(libs.androidx.room.ktx)
//
//    implementation(libs.androidx.lifecycle.viewmodel.compose)

    implementation(libs.bundles.room)
    ksp(libs.room.compiler) // Needed for @Entity, @Dao, @Database processing

    // ViewModel
    implementation(libs.bundles.viewmodel)

    implementation("com.google.dagger:hilt-android:2.57.1")
    ksp("com.google.dagger:hilt-android-compiler:2.57.1")

    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
}