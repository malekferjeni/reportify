import com.android.build.gradle.internal.tasks.manifest.mergeManifests
import com.android.ide.common.repository.main

buildscript {
    repositories {
        google()
        // Ajoutez d'autres référentiels si nécessaire
    }
    dependencies {
        classpath(libs.google.services)
        // Remplacez la ligne suivante avec la version compatible de l'AGP
        classpath("com.android.tools.build:gradle:8.2.2")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2"
    // Ajoutez les autres plugins dont vous avez besoin ici
}

android {
    compileSdk = 34

    defaultConfig {
        applicationId = "com.uni.study"
        minSdk = 25
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    buildToolsVersion = "34.0.0"
    // Configuration des namespaces
    namespace = "http://schemas.android.com/apk/res-auto"
    namespace = "com.uni.study"
    dependenciesInfo {
        includeInApk = true
        includeInBundle = true
    }
    sourceSets {
        getByName("main") {
            manifest.srcFile("src/main/AndroidManifest.xml")
            // Autres configurations des sources...
        }
    }
}

// Dépendances du projet
dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
