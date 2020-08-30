/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    plugin(Deps.Plugins.androidLibrary)
    plugin(Deps.Plugins.kotlinMultiplatform)
    plugin(Deps.Plugins.mobileMultiplatform)
    plugin(Deps.Plugins.mavenPublish)
}

group = "dev.icerock.moko"
version = Deps.mokoFieldsVersion

dependencies {
    commonMainImplementation(Deps.Libs.MultiPlatform.mokoMvvm.common)
    commonMainImplementation(Deps.Libs.MultiPlatform.mokoResources.common)

    // temporary fix of https://youtrack.jetbrains.com/issue/KT-41083
    commonMainImplementation("dev.icerock.moko:parcelize:0.4.0")
    commonMainImplementation("dev.icerock.moko:graphics:0.4.0")

    // fix of Failed to resolve Kotlin library: fields/build/kotlinSourceSetMetadata/commonMain/org.jetbrains.kotlinx-atomicfu-common/org.jetbrains.kotlinx-atomicfu-common-nativeInterop.klib
    commonMainImplementation(Deps.Libs.MultiPlatform.coroutines)

    androidMainImplementation(Deps.Libs.Android.appCompat)
    androidMainImplementation(Deps.Libs.Android.lifecycle)
}

publishing {
    repositories.maven("https://api.bintray.com/maven/icerockdev/moko/moko-fields/;publish=1") {
        name = "bintray"

        credentials {
            username = System.getProperty("BINTRAY_USER")
            password = System.getProperty("BINTRAY_KEY")
        }
    }
}
