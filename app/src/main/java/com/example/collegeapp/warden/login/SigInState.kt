package com.example.collegeapp.warden.login


sealed class SignInState{
    object Nothing: SignInState()
    object Loading: SignInState()
    object Success: SignInState()
    object Error: SignInState()
}
