package com.example.collegeapp.warden.defaulter

import android.Manifest
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.collegeapp.common.model.Students
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AddStudentScreen(navController: NavController) {
    val viewModel: HomeViewModel = hiltViewModel()

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

    var loading by remember {
        mutableStateOf(false)
    }
    var studentImageUri by remember { mutableStateOf<Uri?>(null) }

    var late by remember {
        mutableStateOf(false)
    }

    fun onAddStudent() {
        val student = Students(
            name = name,
            late = late,
            leave = leave,
            regNumber = regNumber,
            hosteler = isHosteler,
            hostel = hostel,
            inTime = inTime,
            date = Students.getCurrentDate(),
            imageUrl = studentImageUri?.toString()?:""
        )

        viewModel.uploadImageAndAddStudent(
            imageUri = studentImageUri,
            student=student,
            onSuccess = {
                name = ""
                regNumber = ""
                isHosteler = false
                hostel = ""
                inTime = ""
                late=false
                leave=false
                errorMessage = "Student added successfully!"
                date=""
                studentImageUri= null
                loading= false
            },
            onError = {
                errorMessage = it
            }
        )
    }

    val chooserDialog = remember {
        mutableStateOf(false)
    }

    val cameraImageUri = remember {
        mutableStateOf<Uri?>(null)
    }

    val cameraImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
         studentImageUri=cameraImageUri.value
        }
    }

    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
                // viewmodel function to be implemented
            studentImageUri= it
        }
    }

    fun createImageUri(): Uri {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = ContextCompat.getExternalFilesDirs(
            navController.context, Environment.DIRECTORY_PICTURES
        ).first()
        return FileProvider.getUriForFile(navController.context,
            "${navController.context.packageName}.provider",
            File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir).apply {
                cameraImageUri.value = Uri.fromFile(this)
            })
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                cameraImageLauncher.launch(createImageUri())
            }
        }

    @Composable
    fun ContentSelectionDialog(onCameraSelected: () -> Unit, onGallerySelected: () -> Unit) {
        AlertDialog(onDismissRequest = { },
            confirmButton = {
                TextButton(onClick = onCameraSelected) {
                    Text(text = "Camera")
                }
            },
            dismissButton = {
                TextButton(onClick = onGallerySelected) {
                    Text(text = "Gallery")
                }
            },
            title = { Text(text = "Select your source?") },
            text = { Text(text = "Would you like to pick an image from the gallery or use the") })
    }
if(loading){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
    else{
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = if (errorMessage == "Student added successfully!") Color.Green else Color.Red,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            if (chooserDialog.value) {
                ContentSelectionDialog(onCameraSelected = {
                    chooserDialog.value = false
                    if (navController.context.checkSelfPermission(Manifest.permission.CAMERA) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                        cameraImageLauncher.launch(createImageUri())
                    } else {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                }, onGallerySelected = {
                    chooserDialog.value = false
                    imageLauncher.launch("image/*")
                })
            }

            Box(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                if (studentImageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(studentImageUri),
                        contentDescription = "Selected Image",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape)
                    )
                } else {
                    IconButton(
                        onClick = { chooserDialog.value = true },
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color.Gray.copy(alpha = 0.2f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Photo",
                            tint = Color.DarkGray,
                            modifier = Modifier.size(60.dp)
                        )
                    }
                }
            }



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
                        late = false
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
                    onCheckedChange = {
                        late = it
                        leave = false
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
                onClick = { onAddStudent()
                          loading =true},
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Add Student")
            }
        }
    }
}
