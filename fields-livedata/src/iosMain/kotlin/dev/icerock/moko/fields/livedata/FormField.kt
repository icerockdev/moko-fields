/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.fields.livedata

import dev.icerock.moko.mvvm.livedata.Closeable
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.addCloseableObserver
import kotlinx.coroutines.DisposableHandle

actual class FormField<D, E> actual constructor(
    initialValue: D,
    validation: (LiveData<D>) -> LiveData<E?>
) : BaseLiveDataFormField<D, E>(initialValue, validation) {
    override fun observeData(
        onChange: (D) -> Unit
    ): DisposableHandle {
        val closeable: Closeable = data.addCloseableObserver(onChange)

        return DisposableHandle {
            closeable.close()
        }
    }

    override fun observeError(
        onChange: (E?) -> Unit
    ): DisposableHandle {
        val closeable: Closeable = error.addCloseableObserver(onChange)

        return DisposableHandle {
            closeable.close()
        }
    }
}
