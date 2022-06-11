/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("com.android.library")
    id("dev.icerock.moko.gradle.android.base")
    id("org.jetbrains.kotlin.multiplatform")
    id("dev.icerock.mobile.multiplatform.targets")
    id("dev.icerock.mobile.multiplatform-resources")
    id("dev.icerock.mobile.multiplatform.ios-framework")
    id("dev.icerock.moko.gradle.detekt")
    id("dev.icerock.moko.gradle.tests")
    id("dev.icerock.moko.kswift")
}

dependencies {
    commonMainApi(libs.coroutines)
    commonMainApi(libs.mokoResources)
    commonMainApi(libs.mokoMvvmCore)
    commonMainApi(libs.mokoMvvmLiveData)
    commonMainApi(libs.mokoMvvmLiveDataResources)
    commonMainApi(projects.fieldsLivedata)
}

multiplatformResources {
    multiplatformResourcesPackage = "com.icerockdev.library"
}

framework {
    export(projects.fieldsLivedata)
    export(libs.mokoResources)
    export(libs.mokoMvvmCore)
    export(libs.mokoMvvmLiveData)
    export(libs.mokoMvvmLiveDataResources)
}

kswift {
    install(dev.icerock.moko.kswift.plugin.feature.PlatformExtensionFunctionsFeature)
}
