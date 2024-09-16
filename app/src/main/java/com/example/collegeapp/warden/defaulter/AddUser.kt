package com.example.collegeapp.warden.defaulter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.collegeapp.common.model.Students

@Composable
fun AddStudentScreen() {
    val viewModel: HomeViewModel = hiltViewModel()
    // State variables for each field
    var name by remember { mutableStateOf("") }
    var regNumber by remember { mutableStateOf("") }
    var isHosteler by remember { mutableStateOf(false) }
    var hostel by remember { mutableStateOf("") }
    var inTime by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var date by remember{ mutableStateOf("") }
  var leave by remember {
      mutableStateOf(false)
  }
    var late by remember {
        mutableStateOf(false)
    }

    // Function to handle student addition
    fun onAddStudent() {
        val student = Students(
            name = name,
            late = late,
            leave = leave,
            regNumber = regNumber,
            hosteler = isHosteler,
            hostel = hostel,
            inTime = inTime,
            date = Students.getCurrentDate()
        )

        // Call ViewModel to add student
        viewModel.addStudent(
            student,
            onSuccess = {
                // Clear the form and show success message
                name = ""
                regNumber = ""
                isHosteler = false
                hostel = ""
                inTime = ""
                late=false
                leave=false
                errorMessage = "Student added successfully!"
                date=""

            },
            onError = {
                // Display error message
                errorMessage = it
            }
        )
    }

    // UI components
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Display Error or Success Message
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = if (errorMessage == "Student added successfully!") Color.Green else Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }

        // Name Input
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Registration Number Input
        OutlinedTextField(
            value = regNumber,
            onValueChange = { regNumber = it },
            label = { Text("Registration Number") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Hostel Checkbox
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isHosteler,
                onCheckedChange = {
                    isHosteler = it

                }
            )
            Text("Is Hosteler")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = leave,
                onCheckedChange = {
                    leave = it
                    late= false


                }
            )
            Text("On Leave")
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = late,
                onCheckedChange = { late= it
                leave=false
                }
            )
            Text("Is Late")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Hostel Name Input (Only if hosteler is true)
        if (isHosteler) {
            OutlinedTextField(
                value = hostel,
                onValueChange = { hostel = it },
                label = { Text("Hostel Name") },
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        // In-Time Input
        OutlinedTextField(
            value = inTime,
            onValueChange = { inTime = it },
            label = { Text("In-Time") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Submit Button
        Button(
            onClick = { onAddStudent() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Student")
        }
    }
}
