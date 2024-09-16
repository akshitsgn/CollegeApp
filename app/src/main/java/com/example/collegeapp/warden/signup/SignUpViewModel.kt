package com.example.collegeapp.warden.signup

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(): ViewModel() {

    private val _state = MutableStateFlow<SignUpState>(SignUpState.Nothing)//nothing is the default status of the state
    val state = _state.asStateFlow()

    fun signUp( email: String, password: String) {
        _state.value = SignUpState.Loading
        // Firebase signIn
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _state.value = SignUpState.Success // Sign-up successful
                } else {
                    _state.value = SignUpState.Error // Sign-up failed
                }
            }
    }
}