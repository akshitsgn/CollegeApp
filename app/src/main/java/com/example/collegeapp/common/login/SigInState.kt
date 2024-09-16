package com.example.collegeapp.common.login


sealed class SignInState{
    object Nothing: SignInState()
    object Loading: SignInState()
    object Success: SignInState()
    object Error: SignInState()
}
