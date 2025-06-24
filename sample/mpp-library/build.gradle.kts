import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import java.util.Locale

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

android {
    namespace = "com.icerockdev.library"
}

dependencies {
    commonMainApi(libs.coroutines)
    commonMainApi(libs.mokoResources)
    commonMainApi(libs.mokoMvvmCore)
    commonMainApi(libs.mokoMvvmLiveData)
    commonMainApi(libs.mokoMvvmLiveDataResources)
    commonMainApi(projects.fieldsLivedata)

    iosMainImplementation("dev.icerock.moko:parcelize:0.9.0")
}

multiplatformResources {
    resourcesPackage.set("com.icerockdev.library")
}

framework {
    export(projects.fieldsLivedata)
    export(libs.mokoResources)
    export(libs.mokoMvvmCore)
    export(libs.mokoMvvmLiveData)
    export(libs.mokoMvvmLiveDataResources)
}

kswift {
    projectPodspecName.set("MultiPlatformLibrary")

    install(dev.icerock.moko.kswift.plugin.feature.PlatformExtensionFunctionsFeature)  {
        filter = includeFilter(
            "PackageFunctionContext/dev.icerock.moko:mvvm-livedata/dev.icerock.moko.mvvm.livedata/Class(name=platform/UIKit/UITextField)/bindTextTwoWay/liveData:Class(name=dev/icerock/moko/mvvm/livedata/MutableLiveData)<Class(name=kotlin/String)>",
            "PackageFunctionContext/dev.icerock.moko:mvvm-livedata-resources/dev.icerock.moko.mvvm.livedata.resources/Class(name=platform/UIKit/UILabel)/bindText/liveData:Class(name=dev/icerock/moko/mvvm/livedata/LiveData)<TypeParameter(id=0)>"
        )
    }
}

kotlin.targets.withType<KotlinNativeTarget>().configureEach {
    binaries.withType<org.jetbrains.kotlin.gradle.plugin.mpp.Framework>().configureEach {
        embedBitcodeMode.set(org.jetbrains.kotlin.gradle.plugin.mpp.BitcodeEmbeddingMode.DISABLE)
        linkTask.doLast {
            val file = File(outputDirectory, "${baseName}Swift")
            val from = file.takeIf { it.exists() } ?: return@doLast
            val to = File(rootDir, "sample/ios-app/kswift")
            from.copyRecursively(to, overwrite = true)
        }
    }
}
