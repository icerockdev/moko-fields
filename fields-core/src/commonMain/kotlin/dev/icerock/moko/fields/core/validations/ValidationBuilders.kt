package dev.icerock.moko.fields.core.validations

import dev.icerock.moko.resources.desc.StringDesc

@Deprecated(
    message = "Deprecated in favor of new DSL-like fieldValidation function",
    level = DeprecationLevel.WARNING,
    replaceWith = ReplaceWith("fieldValidation", "dev.icerock.moko.fields.core.validations")
)
fun <D> fieldValidationBlock(
    block: ValidationResult<D>.() -> ValidationResult<D>
): ((D) -> StringDesc?) = { block(ValidationResult.of(it)).validate() }

fun <D> fieldValidation(
    block: ValidationResult<D>.() -> ValidationResult<D>
): ((D) -> StringDesc?) = { ValidationResult.of(it, block) }
