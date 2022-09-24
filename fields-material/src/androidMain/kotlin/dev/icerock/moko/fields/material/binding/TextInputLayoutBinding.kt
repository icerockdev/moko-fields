/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.fields.material.binding

import android.widget.EditText
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.textfield.TextInputLayout
import dev.icerock.moko.fields.core.FormField
import dev.icerock.moko.mvvm.flow.CStateFlow
import dev.icerock.moko.mvvm.flow.binding.bind
import dev.icerock.moko.mvvm.flow.binding.bindTextTwoWay
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.fields.flow.FormField as FormFieldFlow
import dev.icerock.moko.fields.livedata.FormField as FormFieldLiveData
import dev.icerock.moko.mvvm.livedata.Closeable
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.bindTextTwoWay
import dev.icerock.moko.mvvm.utils.bind
import kotlinx.coroutines.DisposableHandle

fun TextInputLayout.bindError(
    lifecycleOwner: LifecycleOwner,
    flow: CStateFlow<StringDesc?>,
): DisposableHandle {
    return flow.bind(lifecycleOwner) {
        this.error = it?.toString(this.context)
        this.isErrorEnabled = it != null
    }
}

fun TextInputLayout.bindError(
    lifecycleOwner: LifecycleOwner,
    liveData: LiveData<StringDesc?>,
): Closeable {
    return liveData.bind(lifecycleOwner) {
        this.error = it?.toString(this.context)
        this.isErrorEnabled = it != null
    }
}

fun TextInputLayout.bindFormField(
    lifecycleOwner: LifecycleOwner,
    formField: FormField<String, StringDesc>,
) {
    if (formField is FormFieldFlow) {
        return bindFormFieldFlow(
            lifecycleOwner = lifecycleOwner,
            formField = formField
        )
    }

    if (formField is FormFieldLiveData) {
        return bindFormFieldLiveData(
            lifecycleOwner = lifecycleOwner,
            formField = formField
        )
    }
}

private fun TextInputLayout.bindFormFieldFlow(
    lifecycleOwner: LifecycleOwner,
    formField: FormFieldFlow<String, StringDesc>,
) {
    val editText: EditText = this.editText ?: return

    with(editText) {
        this.bindTextTwoWay(lifecycleOwner, formField.data)
        this.setOnFocusChangeListener { _, focused ->
            if (focused) return@setOnFocusChangeListener
            formField.validate()
        }
        bindError(lifecycleOwner, formField.error)
    }
}

private fun TextInputLayout.bindFormFieldLiveData(
    lifecycleOwner: LifecycleOwner,
    formField: FormFieldLiveData<String, StringDesc>,
) {
    val editText: EditText = this.editText ?: return

    with(editText) {
        this.bindTextTwoWay(lifecycleOwner, formField.data)
        this.setOnFocusChangeListener { _, focused ->
            if (focused) return@setOnFocusChangeListener
            formField.validate()
        }
        bindError(lifecycleOwner, formField.error)
    }
}
