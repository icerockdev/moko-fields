/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.fields.core

import kotlinx.coroutines.DisposableHandle

actual interface FormField<D, E> {
    actual var value: D
    actual fun validate(): Boolean
    actual fun setError(error: E?)

    fun observeData(onChange: (D) -> Unit): DisposableHandle
    fun observeError(onChange: (E?) -> Unit): DisposableHandle
}
