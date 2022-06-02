package dev.icerock.moko.fields.flow

import dev.icerock.moko.fields.core.FormField
import dev.icerock.moko.mvvm.flow.CMutableStateFlow
import dev.icerock.moko.mvvm.flow.CStateFlow
import dev.icerock.moko.mvvm.flow.cMutableStateFlow
import dev.icerock.moko.mvvm.flow.cStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

open class FormField<D, E>(
    scope: CoroutineScope, initialValue: D, validationTransform: (D) -> E?
) : FormField<D, E> {

    val data: CMutableStateFlow<D> =
        MutableStateFlow(initialValue).cMutableStateFlow()

    private val validationError: MutableStateFlow<E?> =
        MutableStateFlow(null)

    private val showValidationError: MutableStateFlow<Boolean> =
        MutableStateFlow(false)

    val error: CStateFlow<E?> =
        combine(validationError, showValidationError) { error, show ->
            if (show) error else null
        }.stateIn(scope, SharingStarted.Eagerly, null).cStateFlow()

    val isValid: CStateFlow<Boolean> =
        validationError.map { it == null }
            .stateIn(scope, SharingStarted.Eagerly, true).cStateFlow()

    init {
        data.map { validationTransform(it) }
            .onEach { validationError.value = it }
            .launchIn(scope)
    }

    override fun setError(error: E?) {
        validationError.value = error
    }

    override fun value(): D = data.value

    override fun validate(): Boolean {
        showValidationError.value = true
        return isValid.value
    }
}
