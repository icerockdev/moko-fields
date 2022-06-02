/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("MaximumLineLength")

package com.icerockdev.library

import dev.icerock.moko.fields.core.validate
import dev.icerock.moko.fields.core.validations.ValidationResult
import dev.icerock.moko.fields.core.validations.matchRegex
import dev.icerock.moko.fields.core.validations.minLength
import dev.icerock.moko.fields.core.validations.notBlank
import dev.icerock.moko.fields.livedata.FormField
import dev.icerock.moko.fields.livedata.liveBlock
import dev.icerock.moko.fields.livedata.validations.fieldValidation
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc

class LoginViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>
) : ViewModel(), EventsDispatcherOwner<LoginViewModel.EventsListener> {
    val emailField: FormField<String, StringDesc> = FormField(
        initialValue = "",
        validation = liveBlock { email ->
            ValidationResult.of(email) {
                notBlank(MR.strings.cant_be_blank.desc())
                matchRegex(MR.strings.wrong_format.desc(), EMAIL_REGEX)
            }
        }
    )

    @Suppress("MagicNumber")
    val passwordField: FormField<String, StringDesc> = FormField(
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

        eventsDispatcher.dispatchEvent { showMessage(message.desc()) }
    }

    interface EventsListener {
        fun showMessage(message: StringDesc)
    }

    companion object {
        @Suppress("MaxLineLength")
        private val EMAIL_REGEX =
            Regex("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+")
    }
}
