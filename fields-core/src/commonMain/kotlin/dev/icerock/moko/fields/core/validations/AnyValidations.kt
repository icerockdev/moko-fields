/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.fields.core.validations

import dev.icerock.moko.resources.desc.StringDesc

fun <T : Any> ValidationResult<T?>.notNull(errorText: StringDesc) = nextValidation { value ->
    if (value != null) {
        ValidationResult.success(value)
    } else {
        ValidationResult.failure(errorText)
    }
}

fun <T> ValidationResult<T>.isEqual(errorText: StringDesc, reference: T) = nextValidation { value ->
    if (value == reference) {
        ValidationResult.success(value)
    } else {
        ValidationResult.failure(errorText)
    }
}
