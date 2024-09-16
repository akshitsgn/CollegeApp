package com.example.collegeapp.common.signup

sealed class SignUpState {
    object Nothing: SignUpState()
    object Loading: SignUpState()
    object Success: SignUpState()
    object Error: SignUpState()
}