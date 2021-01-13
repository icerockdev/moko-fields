/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

object Deps {
    private const val kotlinTestVersion = "1.4.21"

    private const val androidAppCompatVersion = "1.1.0"
    private const val materialDesignVersion = "1.0.0"
    private const val androidLifecycleVersion = "2.1.0"
    private const val androidCoreTestingVersion = "2.1.0"

    private const val coroutinesVersion = "1.4.2"
    private const val mokoResourcesVersion = "0.13.2"
    private const val mokoMvvmVersion = "0.9.0"
    const val mokoFieldsVersion = "0.6.0"

    object Android {
        const val compileSdk = 30
        const val targetSdk = 30
        const val minSdk = 16
    }

    object Plugins {
        val androidApplication = GradlePlugin(id = "com.android.application")
        val androidLibrary = GradlePlugin(id = "com.android.library")
        val kotlinMultiplatform = GradlePlugin(id = "org.jetbrains.kotlin.multiplatform")
        val kotlinKapt = GradlePlugin(id = "kotlin-kapt")
        val kotlinAndroid = GradlePlugin(id = "kotlin-android")
        val mavenPublish = GradlePlugin(id = "org.gradle.maven-publish")

        val mobileMultiplatform = GradlePlugin(id = "dev.icerock.mobile.multiplatform")
        val iosFramework = GradlePlugin(id = "dev.icerock.mobile.multiplatform.ios-framework")

        val mokoResources = GradlePlugin(
            id = "dev.icerock.mobile.multiplatform-resources",
            module = "dev.icerock.moko:resources-generator:$mokoResourcesVersion"
        )
    }

    object Libs {
        object Android {
            const val appCompat = "androidx.appcompat:appcompat:$androidAppCompatVersion"
            const val material = "com.google.android.material:material:$materialDesignVersion"
            const val lifecycle = "androidx.lifecycle:lifecycle-extensions:$androidLifecycleVersion"
            const val mokoMvvmDataBinding = "dev.icerock.moko:mvvm-databinding:$mokoMvvmVersion"
        }

        object MultiPlatform {
            const val coroutines =
                "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
            val mokoResources = "dev.icerock.moko:resources:$mokoResourcesVersion"
                .defaultMPL(android = true, ios = true)
            val mokoMvvmCore = "dev.icerock.moko:mvvm-core:$mokoMvvmVersion"
                .defaultMPL(android = true, ios = true)
            val mokoMvvmLiveData = "dev.icerock.moko:mvvm-livedata:$mokoMvvmVersion"
                .defaultMPL(android = true, ios = true)
            const val mokoFields =
                "dev.icerock.moko:fields:$mokoFieldsVersion"
        }

        object Tests {
            const val kotlinTestJUnit =
                "org.jetbrains.kotlin:kotlin-test-junit:$kotlinTestVersion"
            const val androidCoreTesting =
                "androidx.arch.core:core-testing:$androidCoreTestingVersion"
        }
    }
}
