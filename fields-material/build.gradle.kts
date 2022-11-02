/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("dev.icerock.moko.gradle.android.library")
    id("dev.icerock.moko.gradle.detekt")
    id("dev.icerock.moko.gradle.android.publication")
}

dependencies {
    api(projects.fieldsCore)
    api(libs.mokoResources)
    api(libs.lifecycleKtx)
    api(libs.material)
}
