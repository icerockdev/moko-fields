/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 33
    buildFeatures {
        compose = true
    }
    defaultConfig {
        applicationId = "dev.icerock.moko.fields.sample.declarativeui.android"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.4"
    }
}

dependencies {
    implementation(project(":sample-declarative-ui:shared"))

    implementation(platform("androidx.compose:compose-bom:2023.03.00"))

    implementation("androidx.compose.ui:ui")
    // Tooling support (Previews, etc.)
    implementation("androidx.compose.ui:ui-tooling")
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation("androidx.compose.foundation:foundation")
    // Material Design
    implementation("androidx.compose.material:material")
    // Material design icons
    implementation("androidx.compose.material:material-icons-core")
    // Integration with observables
    implementation("androidx.compose.runtime:runtime-livedata")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    implementation("androidx.navigation:navigation-compose:2.5.3")
}
