plugins {
    alias(libs.plugins.android.application)
    id("com.google.dagger.hilt.android")

    id("androidx.navigation.safeargs")
}

android {
    namespace = "com.denprog.reservationsystem"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.denprog.reservationsystem"
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

        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.3")

    implementation("com.google.dagger:hilt-android:2.51.1")
    implementation(libs.activity)
    implementation(libs.legacy.support.v4)
    implementation(libs.recyclerview)
    annotationProcessor("com.google.dagger:hilt-android-compiler:2.51.1")

    val room_version = "2.6.1"
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}