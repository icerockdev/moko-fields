/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("dev.icerock.moko.gradle.multiplatform.mobile")
    id("dev.icerock.moko.gradle.detekt")
    id("dev.icerock.moko.gradle.publication")
    id("dev.icerock.moko.gradle.stub.javadoc")
}

android {
    buildFeatures.viewBinding = true
}

dependencies {
    api(projects.fieldsCore)
    api(projects.fieldsFlow)
    api(projects.fieldsLivedata)
    api(libs.mokoMvvmFlow)
    api(libs.mokoMvvmLiveData)
    implementation(libs.material)
}
