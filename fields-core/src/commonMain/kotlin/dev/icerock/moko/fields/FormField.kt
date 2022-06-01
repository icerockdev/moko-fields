package dev.icerock.moko.fields

interface FormField {
    fun validate()
    val isValidValue: Boolean
}