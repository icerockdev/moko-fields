/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import UIKit
import MultiPlatformLibrary
import MultiPlatformLibraryMvvm
import SkyFloatingLabelTextField

class TestViewController: UIViewController {
    
    @IBOutlet private var emailField: SkyFloatingLabelTextField!
    @IBOutlet private var passwordField: SkyFloatingLabelTextField!
    
    private var viewModel: LoginViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        viewModel = LoginViewModel(
            eventsDispatcher: EventsDispatcher(listener: self)
        )
        
        emailField.bindTextTwoWay(liveData: viewModel.emailField.data)
        emailField.bindError(liveData: viewModel.emailField.error)
        
        passwordField.bindTextTwoWay(liveData: viewModel.passwordField.data)
        passwordField.bindError(liveData: viewModel.passwordField.error)
    }
    
    @IBAction func onLoginPressed() {
        viewModel.onLoginPressed()
    }
    
    deinit {
        viewModel.onCleared()
    }
}

extension TestViewController: LoginViewModelEventsListener {
    func showMessage(message: StringDesc) {
        let alertController = UIAlertController(title: nil,
                                                message: message.localized(),
                                                preferredStyle: .alert)
        alertController.addAction(UIAlertAction(title: "Ok",
                                                style: .default,
                                                handler: nil))
        present(alertController, animated: true, completion: nil)
    }
}
