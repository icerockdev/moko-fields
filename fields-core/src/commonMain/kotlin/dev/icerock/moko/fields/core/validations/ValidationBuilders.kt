/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.fields.core.validations

import dev.icerock.moko.resources.desc.StringDesc

fun <D> fieldValidation(
    block: ValidationResult<D>.() -> ValidationResult<D>
): ((D) -> StringDesc?) = { ValidationResult.of(it, block) }
