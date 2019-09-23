![moko-fields](img/logo.png)  
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0) [![Download](https://api.bintray.com/packages/icerockdev/moko/moko-fields/images/download.svg) ](https://bintray.com/icerockdev/moko/moko-fields/_latestVersion)

# Mobile Kotlin fields
This is a Kotlin MultiPlatform library that add form fields abstraction to implement any input forms
 with validations.

## Table of Contents
- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Samples](#samples)
- [Set Up Locally](#setup-locally)
- [Contributing](#contributing)
- [License](#license)

## Features
TODO

## Requirements
- Gradle version 5.4.1+
- Android API 21+
- iOS version 9.0+

## Installation
root build.gradle  
```groovy
allprojects {
    repositories {
        maven { url = "https://dl.bintray.com/icerockdev/moko" }
    }
}
```

project build.gradle
```groovy
dependencies {
    commonMainApi("dev.icerock.moko:fields:0.1.0")
}
```

settings.gradle  
```groovy
enableFeaturePreview("GRADLE_METADATA")
```

## Usage
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

## Samples
More examples can be found in the [sample directory](sample).

## Set Up Locally 
- In [fields directory](fields) contains `fields` library;
- In [sample directory](sample) contains samples on android, ios & mpp-library connected to apps;
- For test changes locally use `:fields:publishToMavenLocal` gradle task, after it samples will use locally published version.

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