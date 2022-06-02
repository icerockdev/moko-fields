//
//  LoginScreenBody.swift
//  iosApp
//
//  Created by mdubkov on 02.06.2022.
//

import SwiftUI

struct LoginScreenBody: View {
    @Binding var email: String
    @Binding var password: String
    let emailError: String?
    let passwordError: String?
    var onLoginPressed: () -> Void
    
    var body: some View {
        VStack {
            VStack(alignment: .leading) {
                TextField(
                    "Email",
                    text: $email
                ).frame(height: 48)
                    .padding(
                        EdgeInsets(
                            top: 0,
                            leading: 6,
                            bottom: 0,
                            trailing: 6)
                    )
                    .cornerRadius(5)
                    .overlay(
                        RoundedRectangle(cornerRadius: 5)
                            .stroke(lineWidth: 1.0)
                    )
                if let emailError = emailError {
                    Text(emailError)
                        .foregroundColor(.red)
                        .font(.caption)
                }
                TextField(
                    "Password",
                    text: $password
                ).frame(height: 48)
                    .padding(
                        EdgeInsets(
                            top: 0,
                            leading: 6,
                            bottom: 0,
                            trailing: 6)
                    )
                    .cornerRadius(5)
                    .overlay(
                        RoundedRectangle(cornerRadius: 5)
                            .stroke(lineWidth: 1.0)
                    )
                if let passwordError = passwordError {
                    Text(passwordError)
                        .foregroundColor(.red)
                        .font(.caption)
                }
            }
            Button {
                onLoginPressed()
            } label: {
                Text("Enter")
                    .padding()
            }
        }
        .padding()
        
    }
}

struct LoginScreenBody_Previews: PreviewProvider {
    @State static var email: String = ""
    @State static var password: String = ""
    
    static var previews: some View {
        LoginScreenBody(
            email: $email,
            password: $password,
            emailError: nil,
            passwordError: nil,
            onLoginPressed: { }
        )
        LoginScreenBody(
            email: $email,
            password: $password,
            emailError: "email error",
            passwordError: nil,
            onLoginPressed: { }
        )
        LoginScreenBody(
            email: $email,
            password: $password,
            emailError: nil,
            passwordError: "password error",
            onLoginPressed: { }
        )
        LoginScreenBody(
            email: $email,
            password: $password,
            emailError: "email error",
            passwordError: "password error",
            onLoginPressed: { }
        )
    }
}
