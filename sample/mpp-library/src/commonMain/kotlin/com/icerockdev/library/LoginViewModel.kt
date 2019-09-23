/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.icerockdev.library

import dev.icerock.moko.fields.FormField
import dev.icerock.moko.fields.liveBlock
import dev.icerock.moko.fields.validate
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc

class LoginViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>
) : ViewModel(), EventsDispatcherOwner<LoginViewModel.EventsListener> {
    val emailField = FormField<String, StringDesc>("", liveBlock { email ->
        if (email.isBlank()) MR.strings.cant_be_blank.desc()
        else null
    })
    val passwordField = FormField<String, StringDesc>("", liveBlock { password ->
        if (password.isBlank()) MR.strings.cant_be_blank.desc()
        else null
    })

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
}