/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.fields.flow.validations

import dev.icerock.moko.fields.core.validations.ValidationResult
import dev.icerock.moko.fields.core.validations.fieldValidation
import dev.icerock.moko.fields.flow.flowBlock
import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.coroutines.flow.Flow

fun <D> fieldValidation(
    block: ValidationResult<D>.() -> ValidationResult<D>
): ((Flow<D>) -> Flow<StringDesc?>) = flowBlock {
    fieldValidation(block)(it)
}
