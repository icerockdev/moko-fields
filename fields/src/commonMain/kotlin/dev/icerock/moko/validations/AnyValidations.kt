/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.validations

import dev.icerock.moko.resources.desc.StringDesc

fun ValidationResult<Any?>.notNull(errorText: StringDesc) = nextValidation { value ->
    if (value != null) {
        ValidationResult.success(value)
    } else {
        ValidationResult.failure(errorText)
    }
}
