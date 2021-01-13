/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    plugin(Deps.Plugins.androidLibrary)
    plugin(Deps.Plugins.kotlinMultiplatform)
    plugin(Deps.Plugins.mobileMultiplatform)
    plugin(Deps.Plugins.mokoResources)
    plugin(Deps.Plugins.iosFramework)
}

dependencies {
    commonMainApi(Deps.Libs.MultiPlatform.coroutines)
    commonMainApi(Deps.Libs.MultiPlatform.mokoResources.common)
    commonMainApi(Deps.Libs.MultiPlatform.mokoMvvmCore.common)
    commonMainApi(Deps.Libs.MultiPlatform.mokoMvvmLiveData.common)
    commonMainApi(Deps.Libs.MultiPlatform.mokoFields)
}

multiplatformResources {
    multiplatformResourcesPackage = "com.icerockdev.library"
}

framework {
    export(project(":fields"))
    export(Deps.Libs.MultiPlatform.mokoResources)
    export(Deps.Libs.MultiPlatform.mokoMvvmCore)
    export(Deps.Libs.MultiPlatform.mokoMvvmLiveData)
}
