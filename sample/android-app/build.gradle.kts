/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("dev.icerock.moko.gradle.android.application")
    id("dev.icerock.moko.gradle.detekt")
    id("kotlin-kapt")
}

android {
    buildFeatures.dataBinding = true

    namespace = "com.icerockdev.app"

    defaultConfig {
        applicationId = "dev.icerock.moko.samples.fields"

        versionCode = 1
        versionName = "0.1.0"
    }
}

dependencies {
    implementation(libs.appCompat)
    implementation(libs.material)
    implementation(libs.lifecycleViewModelKtx)
    implementation(libs.mokoMvvmDataBinding)

    implementation(projects.fieldsMaterial)
    implementation(projects.sample.mppLibrary)
}
