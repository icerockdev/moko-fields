/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.fields.core

interface FormField<D, E> {
    fun validate(): Boolean
    fun setError(error: E?)
    fun value(): D
}
