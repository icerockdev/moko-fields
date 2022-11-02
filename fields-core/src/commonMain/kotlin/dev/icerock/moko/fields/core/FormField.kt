/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.fields.core

expect interface FormField<D, E> {
    var value: D
    fun validate(): Boolean
    fun setError(error: E?)
}
