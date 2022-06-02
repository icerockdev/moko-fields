package dev.icerock.moko.fields.core

interface FormField<D, E> {
    fun validate(): Boolean
    fun setError(error: E?)
    fun value(): D
}