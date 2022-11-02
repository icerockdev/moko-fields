/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.fields.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import dev.icerock.moko.mvvm.livedata.LiveData
import kotlinx.coroutines.DisposableHandle
import dev.icerock.moko.fields.core.FormField as FormFieldCore

actual class FormField<D, E> actual constructor(
    initialValue: D,
    validation: (LiveData<D>) -> LiveData<E?>
) : BaseLiveDataFormField<D, E>(initialValue, validation), FormFieldCore<D, E> {
    override fun observeData(
        lifecycleOwner: LifecycleOwner,
        onChange: (D) -> Unit
    ): DisposableHandle {
        val androidObserver = Observer<D> { value ->
            onChange(value)
        }
        val androidLiveData = data.ld()

        androidLiveData.observe(lifecycleOwner, androidObserver)

        return DisposableHandle {
            androidLiveData.removeObserver(androidObserver)
        }
    }

    override fun observeError(
        lifecycleOwner: LifecycleOwner,
        onChange: (E?) -> Unit
    ): DisposableHandle {
        val androidObserver = Observer<E?> { value ->
            onChange(value)
        }
        val androidLiveData = error.ld()

        androidLiveData.observe(lifecycleOwner, androidObserver)

        return DisposableHandle {
            androidLiveData.removeObserver(androidObserver)
        }
    }
}
