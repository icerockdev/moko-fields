/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.fields.sample.declarativeui.android

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import dev.icerock.moko.fields.sample.declarativeui.LoginViewModel
import dev.icerock.moko.mvvm.createViewModelFactory
import dev.icerock.moko.mvvm.flow.compose.observeAsActions
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(
        factory = createViewModelFactory { LoginViewModel() }
    )
) {
    val context = LocalContext.current
    val (emailValue, emailSetter) = viewModel.emailField.data.collectAsMutableState()
    val (passwordValue, passwordSetter) = viewModel.passwordField.data.collectAsMutableState()
    val emailError: StringDesc? by viewModel.emailField.error.collectAsState()
    val passwordError: StringDesc? by viewModel.passwordField.error.collectAsState()

    viewModel.actions.observeAsActions { action ->
        when (action) {
            is LoginViewModel.Action.ShowMessage -> {
                Toast.makeText(
                    context,
                    action.message.toString(context),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    LoginScreenBody(
        email = emailValue,
        emailSetter = emailSetter,
        password = passwordValue,
        passwordSetter = passwordSetter,
        emailError = emailError,
        passwordError = passwordError,
        onLoginPressed = { viewModel.onLoginPressed() },
    )
}

@Composable
fun LoginScreenBody(
    email: String,
    emailSetter: (String) -> Unit,
    password: String,
    passwordSetter: (String) -> Unit,
    emailError: StringDesc?,
    passwordError: StringDesc?,
    onLoginPressed: () -> Unit
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                TextField(
                    placeholder = @Composable {
                        Text("Email")
                    },
                    value = email,
                    onValueChange = emailSetter
                )
                if (emailError != null) {
                    Text(
                        text = emailError.toString(context),
                        color = Color.Red
                    )

                }
            }
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                TextField(
                    placeholder = @Composable {
                        Text("Password")
                    },
                    value = password,
                    onValueChange = passwordSetter
                )
                if (passwordError != null) {
                    Text(
                        text = passwordError.toString(context),
                        color = Color.Red
                    )
                }
            }
            Button(
                onClick = onLoginPressed
            ) {
                Text(text = "Login")
            }
        }

    }
}

@Preview(showSystemUi = true, group = "load")
@Composable
fun LoginScreenBody_Preview() {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    LoginScreenBody(
        email = email.value,
        emailSetter = { email.value = it },
        password = password.value,
        passwordSetter = { password.value = it },
        emailError = null,
        passwordError = null,
        onLoginPressed = { },
    )
}

@Preview(showSystemUi = true, group = "load")
@Composable
fun LoginScreenBodyEmailError_Preview() {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    LoginScreenBody(
        email = email.value,
        emailSetter = { email.value = it },
        password = password.value,
        passwordSetter = { password.value = it },
        emailError = "Email error".desc(),
        passwordError = null,
        onLoginPressed = { },
    )
}

@Preview(showSystemUi = true, group = "load")
@Composable
fun LoginScreenBodyPasswordError_Preview() {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    LoginScreenBody(
        email = email.value,
        emailSetter = { email.value = it },
        password = password.value,
        passwordSetter = { password.value = it },
        emailError = null,
        passwordError = "PasswordError".desc(),
        onLoginPressed = { },
    )
}

@Preview(showSystemUi = true, group = "load")
@Composable
fun LoginScreenBodyError_Preview() {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    LoginScreenBody(
        email = email.value,
        emailSetter = { email.value = it },
        password = password.value,
        passwordSetter = { password.value = it },
        emailError = "Email Error".desc(),
        passwordError = "Password Error".desc(),
        onLoginPressed = { },
    )
}
