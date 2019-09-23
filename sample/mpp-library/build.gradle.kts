/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.multiplatform")
    id("dev.icerock.mobile.multiplatform")
    id("dev.icerock.mobile.multiplatform-resources")
}

android {
    compileSdkVersion(Versions.Android.compileSdk)

    defaultConfig {
        minSdkVersion(Versions.Android.minSdk)
        targetSdkVersion(Versions.Android.targetSdk)
    }
}

val libs = listOf(
    Deps.Libs.MultiPlatform.mokoResources,
    Deps.Libs.MultiPlatform.mokoMvvm,
    Deps.Libs.MultiPlatform.mokoFields
)

setupFramework(
    exports = libs
)

dependencies {
    mppLibrary(Deps.Libs.MultiPlatform.kotlinStdLib)

    libs.forEach { mppLibrary(it) }

    androidLibrary(Deps.Libs.Android.lifecycle)
}

multiplatformResources {
    multiplatformResourcesPackage = "com.icerockdev.library"
}
