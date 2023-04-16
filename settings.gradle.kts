/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */
enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
    }
}

rootProject.name = "moko-fields"

include(":fields-core")
include(":fields-flow")
include(":fields-livedata")
include(":fields-material")
include(":sample:android-app")
include(":sample:mpp-library")
include(":sample-declarative-ui:shared")
include(":sample-declarative-ui:androidApp")
