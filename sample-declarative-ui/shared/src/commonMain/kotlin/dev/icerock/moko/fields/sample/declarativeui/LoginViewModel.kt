/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("MaximumLineLength")

package dev.icerock.moko.fields.sample.declarativeui

import dev.icerock.moko.fields.core.validate
import dev.icerock.moko.mvvm.flow.CFlow
import dev.icerock.moko.mvvm.flow.cFlow
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.fields.core.validations.ValidationResult
import dev.icerock.moko.fields.core.validations.matchRegex
import dev.icerock.moko.fields.core.validations.minLength
import dev.icerock.moko.fields.core.validations.notBlank
import dev.icerock.moko.fields.flow.FormField
import dev.icerock.moko.fields.flow.flowBlock
import dev.icerock.moko.fields.flow.validations.fieldValidation
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class LoginViewModel : ViewModel() {

    private val _actions: Channel<Action> = Channel(Channel.BUFFERED)
    val actions: CFlow<Action> get() = _actions.receiveAsFlow().cFlow()

    val emailField: FormField<String, StringDesc> = FormField(
        scope = viewModelScope,
        initialValue = "",
        validation = flowBlock { email ->
            ValidationResult.of(email) {
                notBlank(MR.strings.cant_be_blank.desc())
                matchRegex(MR.strings.wrong_format.desc(), EMAIL_REGEX)
            }
        }
    )

    @Suppress("MagicNumber")
    val passwordField: FormField<String, StringDesc> = FormField(
        scope = viewModelScope,
        initialValue = "",
        validation = fieldValidation {
            notBlank(MR.strings.cant_be_blank.desc())
            minLength(MR.strings.must_contain_more_char.desc(), 4)
        }
    )

    private val fields = listOf(emailField, passwordField)

    fun onLoginPressed() {
        if (!fields.validate()) return

        val email = emailField.value()
        val password = passwordField.value()
        val message = "$email:$password"

        _actions.trySend(Action.ShowMessage(message.desc()))
    }

    sealed interface Action {
        data class ShowMessage(val message: StringDesc) : Action
    }

    companion object {
        @Suppress("MaxLineLength")
        private val EMAIL_REGEX =
            Regex("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+")
    }
}
