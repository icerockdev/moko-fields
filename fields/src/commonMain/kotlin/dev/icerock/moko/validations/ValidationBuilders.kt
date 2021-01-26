/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.validations

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.resources.desc.StringDesc

@Deprecated(
    message = "Deprecated in favor of new DSL-like fieldValidation function",
    level = DeprecationLevel.WARNING,
    replaceWith = ReplaceWith("fieldValidation", "dev.icerock.moko.validations")
)
fun <D> fieldValidationBlock(
    block: ValidationResult<D>.() -> ValidationResult<D>
): ((LiveData<D>) -> LiveData<StringDesc?>) {
    return { liveData ->
        liveData.map {
            block(ValidationResult.of(it)).validate()
        }
    }
}

fun <D> fieldValidation(
    block: ValidationResult<D>.() -> ValidationResult<D>
): ((LiveData<D>) -> LiveData<StringDesc?>) {
    return { liveData ->
        liveData.map {
            block(ValidationResultDslContext(it)).validate()
        }
    }
}
