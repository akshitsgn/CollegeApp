package com.example.collegeapp.warden.login

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor():ViewModel(){

    private val _state = MutableStateFlow<SignInState>(SignInState.Nothing)//nothing is the default status of the state
    val state = _state.asStateFlow()

    fun signIn(email: String, password: String) {
        _state.value = SignInState.Loading

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result.user?.let {
                        _state.value = SignInState.Success
                        return@addOnCompleteListener
                    }
                    _state.value = SignInState.Error

                } else {
                    _state.value = SignInState.Error
                }
            }
    }

    fun signOut() {
        FirebaseAuth.getInstance().signOut()
        _state.value = SignInState.Nothing // Update the state to show logged-out status if needed
    }
}