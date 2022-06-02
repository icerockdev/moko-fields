package dev.icerock.moko.fields

interface AnyFormField<D, E> {
    val isValidValue: Boolean
    fun validate()
    fun setError(error: E?)
    fun value(): D
}