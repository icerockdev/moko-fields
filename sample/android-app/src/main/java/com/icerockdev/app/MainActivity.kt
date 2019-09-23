/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.icerockdev.app

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.icerockdev.app.databinding.ActivityMainBinding
import com.icerockdev.library.LoginViewModel
import dev.icerock.moko.mvvm.MvvmEventsActivity
import dev.icerock.moko.mvvm.createViewModelFactory
import dev.icerock.moko.mvvm.dispatcher.eventsDispatcherOnMain
import dev.icerock.moko.resources.desc.StringDesc

class MainActivity :
    MvvmEventsActivity<ActivityMainBinding, LoginViewModel, LoginViewModel.EventsListener>(),
    LoginViewModel.EventsListener {
    override val layoutId: Int = R.layout.activity_main
    override val viewModelClass: Class<LoginViewModel> = LoginViewModel::class.java
    override val viewModelVariableId: Int = BR.viewModel

    override fun viewModelFactory(): ViewModelProvider.Factory {
        return createViewModelFactory { LoginViewModel(eventsDispatcherOnMain()) }
    }

    override fun showMessage(message: StringDesc) {
        Toast.makeText(this, message.toString(this), Toast.LENGTH_SHORT).show()
    }
}
