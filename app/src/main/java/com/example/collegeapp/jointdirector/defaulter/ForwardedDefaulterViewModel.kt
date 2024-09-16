package com.example.collegeapp.jointdirector.defaulter

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.collegeapp.common.model.Students
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class ForwardedDefaulterViewModel @Inject constructor(): ViewModel(){
    private val firebaseDatabase = Firebase.database
    private val _users = MutableStateFlow<List<Students>>(emptyList())
    val users = _users.asStateFlow()
    init{
        listenToUserChanges()
    }
    private fun listenToUserChanges() {
        firebaseDatabase.getReference("forwardedstudents").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val list = mutableListOf<Students>()
                dataSnapshot.children.forEach { data ->
                    val user = data.getValue(Students::class.java)
                    user?.let { list.add(it) }
                }
                _users.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", "Failed to read value: ${error.message}")
                // Handle possible errors.
            }
        })
    }

    }
