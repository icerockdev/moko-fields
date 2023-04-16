/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.fields.core

import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.DisposableHandle

actual interface FormField<D, E> {
    actual fun validate(): Boolean
    actual fun value(): D
    actual fun setValue(value: D)
    actual fun setError(error: E?)
    actual fun resetValidation()
    fun observeData(lifecycleOwner: LifecycleOwner, onChange: (D) -> Unit): DisposableHandle
    fun observeError(lifecycleOwner: LifecycleOwner, onChange: (E?) -> Unit): DisposableHandle
}
