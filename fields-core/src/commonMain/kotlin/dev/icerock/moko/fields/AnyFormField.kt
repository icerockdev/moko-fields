package dev.icerock.moko.fields

interface AnyFormField {
    fun validate()
    val isValidValue: Boolean
}