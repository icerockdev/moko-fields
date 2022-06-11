/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.fields.livedata.validations

import dev.icerock.moko.fields.core.validations.ValidationResult
import dev.icerock.moko.fields.core.validations.fieldValidation
import dev.icerock.moko.fields.livedata.liveBlock
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.resources.desc.StringDesc

fun <D> fieldValidation(
    block: ValidationResult<D>.() -> ValidationResult<D>
): ((LiveData<D>) -> LiveData<StringDesc?>) = liveBlock {
    fieldValidation(block)(it)
}
