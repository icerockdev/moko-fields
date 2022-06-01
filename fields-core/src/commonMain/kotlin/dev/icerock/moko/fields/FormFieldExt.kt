package dev.icerock.moko.fields

fun List<FormField>.validate(): Boolean {
    forEach { it.validate() }
    return all { it.isValidValue }
}