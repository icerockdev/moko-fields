/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.fields.flow

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import dev.icerock.moko.fields.core.FormFieldAndroid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

open class FormFieldAndroid<D, E>(
    scope: CoroutineScope,
    initialValue: D,
    validation: (Flow<D>) -> Flow<E?>
) : FormField<D, E>(scope, initialValue, validation), FormFieldAndroid<D, E> {

    override fun observeData(
        lifecycleOwner: LifecycleOwner,
        onChange: (D) -> Unit
    ): DisposableHandle {
        val job: Job = lifecycleOwner.lifecycleScope.launchWhenStarted {
            data.onEach { onChange(it) }.collect()
        }

        return DisposableHandle {
            job.cancel()
        }
    }

    override fun observeError(
        lifecycleOwner: LifecycleOwner,
        onChange: (E?) -> Unit
    ): DisposableHandle {
        val job: Job = lifecycleOwner.lifecycleScope.launchWhenStarted {
            error.onEach { onChange(it) }.collect()
        }

        return DisposableHandle {
            job.cancel()
        }
    }
}
