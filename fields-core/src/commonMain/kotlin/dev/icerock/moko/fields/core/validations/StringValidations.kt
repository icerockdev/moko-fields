/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.fields.core.validations

import dev.icerock.moko.resources.desc.StringDesc

fun ValidationResult<String>.notEmpty(errorText: StringDesc) = nextValidation { value ->
    if (value.isNotEmpty()) {
        ValidationResult.success(value)
    } else {
        ValidationResult.failure(errorText)
    }
}

fun ValidationResult<String>.notBlank(errorText: StringDesc) = nextValidation { value ->
    if (value.isNotBlank()) {
        ValidationResult.success(value)
    } else {
        ValidationResult.failure(errorText)
    }
}

fun ValidationResult<String>.minLength(errorText: StringDesc, minLength: Int = 0) =
    nextValidation { value ->
        if (value.length < minLength) {
            ValidationResult.failure(errorText)
        } else {
            ValidationResult.success(value)
        }
    }

fun ValidationResult<String>.maxLength(errorText: StringDesc, maxLength: Int = 0) =
    nextValidation { value ->
        if (value.length > maxLength) {
            ValidationResult.failure(errorText)
        } else {
            ValidationResult.success(value)
        }
    }

fun ValidationResult<String>.matchRegex(errorText: StringDesc, regex: Regex) =
    nextValidation { value ->
        if (regex.matches(value)) {
            ValidationResult.success(value)
        } else {
            ValidationResult.failure(errorText)
        }
    }

fun ValidationResult<String>.containedIn(errorText: StringDesc, validValues: List<String>) =
    nextValidation { value ->
        if (validValues.contains(value)) {
            ValidationResult.success(value)
        } else {
            ValidationResult.failure(errorText)
        }
    }
