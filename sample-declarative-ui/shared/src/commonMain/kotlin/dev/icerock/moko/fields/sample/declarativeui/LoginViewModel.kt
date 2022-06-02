/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("MaximumLineLength")

package dev.icerock.moko.fields.sample.declarativeui

import dev.icerock.moko.fields.StateFormField
import dev.icerock.moko.fields.validate
import dev.icerock.moko.mvvm.flow.CFlow
import dev.icerock.moko.mvvm.flow.cFlow
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.validations.ValidationResult
import dev.icerock.moko.validations.fieldValidation
import dev.icerock.moko.validations.matchRegex
import dev.icerock.moko.validations.minLength
import dev.icerock.moko.validations.notBlank
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class LoginViewModel : ViewModel() {

    private val _actions: Channel<Action> = Channel(Channel.BUFFERED)
    val actions: CFlow<Action> get() = _actions.receiveAsFlow().cFlow()

    val emailField: StateFormField<String, StringDesc> = StateFormField(
        scope = viewModelScope,
        initialValue = "",
        validationTransform = { email ->
            ValidationResult.of(email) {
                notBlank(MR.strings.cant_be_blank.desc())
                matchRegex(MR.strings.wrong_format.desc(), EMAIL_REGEX)
            }
        }
    )

    @Suppress("MagicNumber")
    val passwordField: StateFormField<String, StringDesc> = StateFormField(
        scope = viewModelScope,
        initialValue = "",
        validationTransform = fieldValidation {
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
        data class ShowMessage(val message: StringDesc): Action
    }

    companion object {
        @Suppress("MaxLineLength")
        private val EMAIL_REGEX =
            Regex("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+")
    }
}
