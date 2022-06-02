package dev.icerock.moko.fields

fun List<AnyFormField>.validate(): Boolean {
    forEach { it.validate() }
    return all { it.isValidValue }
}