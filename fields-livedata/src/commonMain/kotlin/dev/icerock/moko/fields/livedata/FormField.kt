/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.fields.livedata

import dev.icerock.moko.fields.core.FormField
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MediatorLiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.distinct
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.mvvm.livedata.mediatorOf

open class FormField<D, E>(
    initialValue: D,
    validation: (LiveData<D>) -> LiveData<E?>
) : FormField<D, E> {

    val data = MutableLiveData(initialValue)

    private val validationError: MutableLiveData<E?> by lazy {
        MediatorLiveData<E?>(null).apply {
            addSource(validation(data.distinct())) {
                value = it
            }
        }
    }

    private val showValidationError = MutableLiveData(false)

    val error: LiveData<E?> by lazy {
        mediatorOf(validationError, showValidationError) { error, show ->
            if (show) error else null
        }
    }

    val isValid: LiveData<Boolean> by lazy {
        validationError.map { it == null }
    }

    override fun setError(error: E?) {
        validationError.value = error
    }

    override var value: D = data.value

    override fun validate(): Boolean {
        showValidationError.value = true
        return isValid.value
    }

    @Deprecated(
        message = "Deprecated. Please, use \"value\" field",
        replaceWith = ReplaceWith("value", "dev.icerock.moko.fields.livedata.FirmField.value"),
        level = DeprecationLevel.WARNING
    )
    fun value(): D = value
}

fun <D, E> liveBlock(block: (D) -> E?): ((LiveData<D>) -> LiveData<E?>) {
    return { liveData ->
        liveData.map { block(it) }
    }
}
