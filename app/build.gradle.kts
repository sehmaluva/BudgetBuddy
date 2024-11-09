plugins {
    alias(libs.plugins.android.application)
     // Add this line for Kotlin annotation processing
}

android {
    namespace = "com.budgetbuddy"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.budgetbuddy"
        minSdk = 30
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation("androidx.room:room-runtime:2.5.0")
    annotationProcessor("androidx.room:room-compiler:2.5.0")
    // For Kotlin use kapt instead of annotationProcessor

    
    // Room dependencies
    implementation(libs.room.runtime)
     // Use kapt for Kotlin

    // Room KTX
    implementation(libs.room.ktx)

    // ViewModel
    implementation(libs.lifecycle.viewmodel)

    // LiveData
    implementation(libs.lifecycle.livedata)

    // Lifecycle compiler (if needed)
     // Use kapt for Kotlin

    // Other dependencies
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
