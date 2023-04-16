/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.fields.flow

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import dev.icerock.moko.fields.core.FormField as FormFieldCore

actual class FormField<D, E> actual constructor(
    scope: CoroutineScope,
    initialValue: D,
    validation: (Flow<D>) -> Flow<E?>
) : BaseFlowFormField<D, E>(scope, initialValue, validation), FormFieldCore<D, E> {

    override fun observeData(
        lifecycleOwner: LifecycleOwner,
        onChange: (D) -> Unit
    ): DisposableHandle {
        val job: Job = lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                data.onEach { onChange(it) }.collect()
            }
        }

        return DisposableHandle {
            job.cancel()
        }
    }

    override fun observeError(
        lifecycleOwner: LifecycleOwner,
        onChange: (E?) -> Unit
    ): DisposableHandle {
        val job: Job = lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                error.onEach { onChange(it) }.collect()
            }
        }

        return DisposableHandle {
            job.cancel()
        }
    }
}
