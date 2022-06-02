//
//  LoginUI.swift
//  iosApp
//
//  Created by mdubkov on 02.06.2022.
//

import SwiftUI
import MultiPlatformLibrary
import mokoMvvmFlowSwiftUI
import Combine

struct LoginScreen: View {
    @StateObject private var viewModel: LoginViewModel
    @State var alertShowed: Bool = false
    @State var alertMessage: String = ""
    
    init(viewModel: LoginViewModel) {
        _viewModel = StateObject(wrappedValue: viewModel)
    }
    
    var body: some View {
        LoginScreenBody(
            email: viewModel.binding(\.emailField.data),
            password: viewModel.binding(\.passwordField.data),
            emailError: viewModel.stateNullable(\.emailField.error),
            passwordError: viewModel.stateNullable(\.passwordField.error),
            onLoginPressed: { viewModel.onLoginPressed() }
        ).onReceive(viewModel.actionsKs) { action in
            switch action {
            case .showMessage(let messageData):
                alertShowed = true
                alertMessage = messageData.message.localized()
            }
        }.alert(isPresented: $alertShowed) {
            Alert(title: Text("Message"), message: Text(alertMessage), dismissButton: .default(Text("Ok")))
        }
        
    }
}

extension LoginViewModel {
    var actionsKs: AnyPublisher<LoginViewModelActionKs, Never> {
        get {
            return createPublisher(self.actions)
                .map { LoginViewModelActionKs($0) }
                .eraseToAnyPublisher()
        }
    }
}
