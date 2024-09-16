package com.example.collegeapp.common.signup

import androidx.lifecycle.ViewModel
import com.example.collegeapp.common.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(): ViewModel() {

    private val _state = MutableStateFlow<SignUpState>(SignUpState.Nothing)//nothing is the default status of the state
    val state = _state.asStateFlow()

    private val _userRole = MutableStateFlow<String?>(null) // Holds the fetched user role
    val userRole = _userRole.asStateFlow()

    fun signUp(email: String, password: String, role: String) {
        _state.value = SignUpState.Loading

        // Firebase Auth sign-up
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Get user ID
                    val uid = task.result?.user?.uid ?: return@addOnCompleteListener

                    // Create User object with role
                    val user = User(uid = uid, email = email, role = role)

                    // Save user data to Firebase Realtime Database
                    FirebaseDatabase.getInstance().getReference("users")
                        .child(uid)
                        .setValue(user)
                        .addOnCompleteListener { dbTask ->
                            if (dbTask.isSuccessful) {
                                _state.value = SignUpState.Success // Data saved successfully
                            } else {
                                _state.value = SignUpState.Error // Failed to save user data
                            }
                        }
                } else {
                    _state.value = SignUpState.Error // Sign-up failed
                }
            }
    }

    fun fetchUserRole(uid: String) {
        val userRef = FirebaseDatabase.getInstance().getReference("users").child(uid)
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                _userRole.value = user?.role // Update the role in the state flow
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle possible errors
                _userRole.value = null
            }
        })
    }
}