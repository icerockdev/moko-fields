/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

object Versions {
    object Android {
        const val compileSdk = 28
        const val targetSdk = 28
        const val minSdk = 16
    }

    const val kotlin = "1.3.70"

    private const val mokoResources = "0.9.0"

    object Plugins {
        const val kotlin = Versions.kotlin
        const val mokoResources = Versions.mokoResources
    }

    object Libs {
        object Android {
            const val appCompat = "1.1.0"
            const val lifecycle = "2.0.0"
            const val material = "1.0.0"
        }

        object MultiPlatform {
            const val mokoResources = Versions.mokoResources
            const val mokoMvvm = "0.6.0"
            const val mokoFields = "0.3.0"
        }
    }
}
