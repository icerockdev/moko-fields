![moko-fields](img/logo.png)  
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0) [![Download](https://img.shields.io/maven-central/v/dev.icerock.moko/fields-core) ](https://repo1.maven.org/maven2/dev/icerock/moko/fields-core) ![kotlin-version](https://kotlin-version.aws.icerock.dev/kotlin-version?group=dev.icerock.moko&name=fields-core)

# Mobile Kotlin fields
This is a Kotlin MultiPlatform library that add form fields abstraction to implement any input forms
 with validations.

## Table of Contents
- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Samples](#samples)
- [Set Up Locally](#set-up-locally)
- [Contributing](#contributing)
- [License](#license)

## Features
- Input field abstraction;
- Validation based on reactive approach (on `LiveData` from `moko-mvvm` or `Flow` from `kotlinx.coroutines`).

## Requirements
- Gradle version 6.8+
- Android API 16+
- iOS version 11.0+

## Installation
root build.gradle  
```groovy
allprojects {
    repositories {
      mavenCentral()
    }
}
```

project build.gradle
```groovy
dependencies {
    commonMainApi("dev.icerock.moko:fields-core:0.10.0")
    commonMainApi("dev.icerock.moko:fields-livedata:0.10.0")
    commonMainApi("dev.icerock.moko:fields-flow:0.10.0")
}
```

### Flow additions
to work correctly on the iOS side, you need to export the 
`mvvm-flow` and `mvvm-core` dependencies to the iOS framework.

## Usage

### Live Data 
Create `FormField` to text input with empty validation:
```kotlin
val textField = FormField<String, StringDesc>("", { inputLiveData ->
    inputLiveData.map { text ->
        if (text.isBlank()) "should be not blank!".desc()
        else null
    }
})
```
Use `liveBlock` to simplify validation create.
```kotlin
val textField = FormField<String, StringDesc>("", liveBlock { text ->
    if (text.isBlank()) "should be not blank!".desc()
    else null
})
```
Use `LiveData` in validation lambda to merge with other fields.
```kotlin
val passwordField = FormField<String, StringDesc>("", { inputLiveData ->
    inputLiveData.map { text ->
        if (text.isBlank()) "should be not blank!".desc()
        else null
    }
})
val passwordConfirmField = FormField<String, StringDesc>("", { inputLiveData ->
    passwordField.data.mergeWith(inputLiveData) { password, passwordConfirm ->
        if (passwordConfirm.isBlank()) "should be not blank!".desc()
        else if(passwordConfirm != password) "passwords not same".desc()
        else null
    }
})
``` 
Call validate to perform validations and show error to user by `field.error` LiveData.
```kotlin
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
```
Bind `FormField` to UI by `data` and `error` `LiveData`s.
```xml
<com.google.android.material.textfield.TextInputLayout
    app:error="@{viewModel.emailField.error.ld}">

    <EditText
        android:text="@={viewModel.emailField.data.ld}" />
</com.google.android.material.textfield.TextInputLayout>
```
```swift
emailField.bindTextTwoWay(liveData: viewModel.emailField.data)
emailField.bindError(liveData: viewModel.emailField.error)
```

### Flow 

Create `FormField` to text input with empty validation:
```kotlin
val emailField: FormField<String, StringDesc> = FormField(
    scope = viewModelScope,
    initialValue = "",
    validationTransform = { email ->
        ValidationResult.of(email) {
            notBlank(MR.strings.cant_be_blank.desc())
            matchRegex(MR.strings.wrong_format.desc(), EMAIL_REGEX)
        }
    }
)
```
FormField to work with `coroutines`, `CoroutineScope` is required.

Call validate to perform validations and show error to user by `field.error` StateFlow.
```kotlin
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
```

Bind FormField to UI by data and error `StateFlow`s: 

```kotlin
val email: String by viewModel.emailField.data.collectAsState()

TextField(
    placeholder = @Composable { 
        Text("Email") 
    }, 
    value = email, 
    onValueChange = { viewModel.emailField.data.value = it }
)
```

For ease of use in working with SwiftUI, you can use [this CocoaPods
dependency](https://github.com/icerockdev/moko-mvvm#swiftui-additions).
```swift
struct LoginScreen: View {
    @StateObject var viewModel: LoginViewModel = LoginViewModel()
    
    var body: some View {
        LoginScreenBody(
             email: viewModel.binding(\.emailField.data),
             emailError: viewModel.state(\.emailField.error)
        )
    }
}

struct LoginScreenBody: View {
    @Binding var email: String
    let emailError: StringDesc?
    
    var body: some View {
        VStack {
            TextField("email", text: viewModel.binding(\.passwordField.data))
            if let emailError = emailError {
                Text(emailError.localized())
            }
        }
    }
}
```

#### Validations packet

There is a useful `ValidationResult` class for building validation monads for a form fields.
Two formats for creating validation are implemented:

- Chain/monad validation:

```kotlin
ValidationResult.of(emailFieldValue)
    .notBlank(blankErrorStringDesc)
    .matchRegex(wrongEmailErrorStringDesc, EMAIL_REGEX)
    .validate()
```

**For this variant, do not forget to call function `validate` at the end!**

- DSL validation:
```kotlin
ValidationResult.of(emailFieldValue) {
    notBlank(blankErrorStringDesc)
    matchRegex(wrongEmailErrorStringDesc, EMAIL_REGEX)
}
```

To create a new function for validation monad, you need to create an extension function of class
`ValidationResult` using builder `nextValidation`. For example, this is how the ready-made function
for checking `String` values for blankness looks like:

```kotlin
fun ValidationResult<String>.notBlank(errorText: StringDesc) = nextValidation { value ->
    if (value.isNotBlank()) {
        ValidationResult.success(value)
    } else {
        ValidationResult.failure(errorText)
    }
}
```

All the ready-made validation functions of the library can be found in the source codes in the files
`AnyValidations.kt` for `Any` class and `StringValidations.kt` for `String` class.

To simplify of adding validation to the `FormField` object (without mapping of a `LiveData`
objects) you can use the builder-function `fieldValidation`:

```kotlin
val passwordField = FormField<String, StringDesc>(
    initialValue = "",
    validation = fieldValidation {
        notBlank(MR.strings.cant_be_blank.desc())
        minLength(MR.strings.must_contain_more_char.desc(), 4)
    }
)
```

## Samples
More examples can be found in the [sample directory](sample) or [sample-declarative-ui directory](sample-declarative-ui).

## Set Up Locally 
- In [fields-core directory](fields-core) contains the core - validations logic and the interface for interacting with fields;
- In [fields-flow directory](fields-flow) contains implementation of the FormField interface using `kotlinx.coroutines`;
- In [fields-livedata directory](fields-livedata) contains implementation of the FormField interface using `moko-mvvm` `LiveData`s
- In [sample directory](sample) contains samples use `fields-livedata` on Android, iOS & mpp-library connected to apps
- In [sample-declarative-ui directory](sample-declarative-ui) contains samples use `fields-flow` on Android with Compose,on iOS with SwiftUI and shared module connected to apps

## Contributing
All development (both new features and bug fixes) is performed in `develop` branch. This way `master` sources always contain sources of the most recently released version. Please send PRs with bug fixes to `develop` branch. Fixes to documentation in markdown files are an exception to this rule. They are updated directly in `master`.

The `develop` branch is pushed to `master` during release.

More detailed guide for contributers see in [contributing guide](CONTRIBUTING.md).

## License
        
    Copyright 2019 IceRock MAG Inc
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
