/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.fields.material.binding

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.textfield.TextInputLayout
import dev.icerock.moko.fields.core.FormFieldAndroid
import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.coroutines.DisposableHandle

fun TextInputLayout.bindFormField(
    lifecycleOwner: LifecycleOwner,
    formField: FormFieldAndroid<String, StringDesc>,
): DisposableHandle {
    val watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) = Unit
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val str = s.toString()
            if (str == formField.value) return

            formField.value = str
        }
    }

    this.editText?.addTextChangedListener(watcher)

    val dataObserver: DisposableHandle = formField.observeData(lifecycleOwner) { value: String ->
        this.editText?.text = Editable.Factory.getInstance().newEditable(value)
    }

    this.editText?.setOnFocusChangeListener { _, hasFocus ->
        if (hasFocus) return@setOnFocusChangeListener
        formField.validate()
    }
    val errorObserver: DisposableHandle =
        formField.observeError(lifecycleOwner) { errorMessage: StringDesc? ->
            error = errorMessage?.toString(context)
            isErrorEnabled = errorMessage != null
        }


    return DisposableHandle {
        this.editText?.removeTextChangedListener(watcher)
        this.editText?.onFocusChangeListener = null
        dataObserver.dispose()
        errorObserver.dispose()
    }
}