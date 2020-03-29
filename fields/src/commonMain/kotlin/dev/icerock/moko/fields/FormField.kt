/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.fields

import dev.icerock.moko.mvvm.livedata.*

open class FormField<D, E>(initialValue: D, validation: (LiveData<D>) -> LiveData<E?>) {

    val data = MutableLiveData(initialValue)

    private val validationError: MutableLiveData<E?> = MediatorLiveData<E?>(null).apply {
        addSource(validation(data.distinct())) {
            value = it
        }
    }
    private val showValidationError = MutableLiveData(false)

    val error: LiveData<E?> = validationError.mergeWith(showValidationError) { error, show ->
        if (show) error else null
    }
    val isValid: LiveData<Boolean> = validationError.map { it == null }

    fun setError(error: E?) {
        validationError.value = error
    }

    fun value(): D = data.value

    fun validate() {
        showValidationError.value = true
    }
}

fun <D, E> liveBlock(block: (D) -> E?): ((LiveData<D>) -> LiveData<E?>) {
    return { liveData ->
        liveData.map { block(it) }
    }
}