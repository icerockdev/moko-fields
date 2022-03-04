/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import UIKit
import MultiPlatformLibrary

class TestViewController: UIViewController {
    
    @IBOutlet private var emailField: UITextField!
    @IBOutlet private var emailErrorLabel: UILabel!
    @IBOutlet private var passwordField: UITextField!
    @IBOutlet private var passwordErrorLabel: UILabel!
    
    private var viewModel: LoginViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        viewModel = LoginViewModel(
            eventsDispatcher: EventsDispatcher(listener: self)
        )
        
        emailField.bindTextTwoWay(liveData: viewModel.emailField.data)
        emailErrorLabel.bindText(liveData: viewModel.emailField.error)
        
        passwordField.bindTextTwoWay(liveData: viewModel.passwordField.data)
        passwordErrorLabel.bindText(liveData: viewModel.passwordField.error)
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
