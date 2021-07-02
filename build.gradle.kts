/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

buildscript {
    repositories {
        mavenCentral()
        google()
    }
    dependencies {
        classpath("dev.icerock.moko:resources-generator:0.16.1")
        classpath(":fields-build-logic")
    }
}

allprojects {
    plugins.withId("org.gradle.maven-publish") {
        group = "dev.icerock.moko"
        version = libs.versions.mokoFieldsVersion.get()
    }
}

tasks.register("clean", Delete::class).configure {
    delete(rootProject.buildDir)
}
