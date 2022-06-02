package dev.icerock.moko.fields.core

fun List<FormField<*, *>>.validate(): Boolean =
    all { it.validate() }
