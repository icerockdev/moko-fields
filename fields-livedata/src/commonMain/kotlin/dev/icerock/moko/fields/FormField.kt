/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.fields

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("FormField", "dev.icerock.moko.fields.livedata"),
    level = DeprecationLevel.WARNING
)
typealias FormField<D, E> = dev.icerock.moko.fields.livedata.FormField<D, E>
