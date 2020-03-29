/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.validations

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.resources.desc.StringDesc

sealed class ValidationResult<out V : Any?> {

    class Success<out V : Any?>(val value: V) : ValidationResult<V>()
    class Failure(val failureText: StringDesc) : ValidationResult<Nothing>()

    companion object {
        fun <V : Any?> success(value: V) = Success(value)

        fun failure(failureText: StringDesc) = Failure(failureText)

        fun <V : Any?> of(value: V): ValidationResult<V> {
            return success(value)
        }
    }
}

inline fun <V : Any?> ValidationResult<V>.nextValidation(
    block: (value: V) -> ValidationResult<V>
): ValidationResult<V> {
    return if (this is ValidationResult.Success) {
        block(this.value)
    } else {
        this
    }
}

fun <V : Any?> ValidationResult<V>.validate(): StringDesc? {
    return if (this is ValidationResult.Failure) {
        this.failureText
    } else {
        null
    }
}

fun <D> fieldValidationBlock(
    block: ValidationResult<D>.() -> ValidationResult<D>
): ((LiveData<D>) -> LiveData<StringDesc?>) {
    return { liveData ->
        liveData.map {
            block(ValidationResult.of(it)).validate()
        }
    }
}
