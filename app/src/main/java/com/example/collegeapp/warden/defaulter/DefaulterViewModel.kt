package com.example.collegeapp.warden.defaulter



import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.collegeapp.common.model.Students
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {

    private val firebaseStorage = FirebaseStorage.getInstance().reference
    private val firebaseDatabase = Firebase.database
    private val _users = MutableStateFlow<List<Students>>(emptyList())
    val users = _users.asStateFlow()

    val hostelersCount = users.map { list ->
        list.count {it.hosteler } // Example filter based on a condition
    }

    val dayHostelersCount = users.map { list ->
        list.count { !it.hosteler }
    }

    val leaveCount = users.map { list ->
        list.count { it.leave }
    }

    val lateCount = users.map { list ->
        list.count { it.late }
    }

    val totalStudents = users.map { list ->
        list.size
    }

    init {
        listenToUserChanges()
    }

    private fun listenToUserChanges() {
        firebaseDatabase.getReference("students").addValueEventListener(object :
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

    fun addStudent(student: Students, onSuccess: () -> Unit, onError: (String) -> Unit) {
        val key = firebaseDatabase.getReference("students").push().key
        if (key == null) {
            onError("Failed to generate a key")
            return
        }
        val studentWithId = student.copy(id = key)
        firebaseDatabase.getReference("students").child(key).setValue(studentWithId)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Unknown error occurred")
            }
    }


    fun deleteStudent(student: Students) {
        firebaseDatabase.getReference("students").child(student.id).removeValue()
            .addOnSuccessListener {
                Log.d("DeleteStudent", "Student ${student.name} deleted successfully")
            }
            .addOnFailureListener {
                Log.e("DeleteStudent", "Failed to delete student: ${it.message}")
            }
    }

    fun forwardAndDeleteStudent(student: Students) {
        val forwardedStudentsRef = firebaseDatabase.getReference("forwardedstudents")
        val originalStudentsRef = firebaseDatabase.getReference("students").child(student.id)

        // Add the student to the new path (forwarded_students)
        forwardedStudentsRef.child(student.id).setValue(student)
            .addOnSuccessListener {
                Log.d("ForwardStudent", "Student ${student.name} forwarded successfully")
                // If successful, delete the student from the original list (students)
                originalStudentsRef.removeValue()
                    .addOnSuccessListener {
                        Log.d("DeleteStudent", "Student ${student.name} deleted successfully")
                    }
                    .addOnFailureListener {
                        Log.e("DeleteStudent", "Failed to delete student: ${it.message}")
                    }
            }
            .addOnFailureListener {
                Log.e("ForwardStudent", "Failed to forward student: ${it.message}")
            }
    }

    fun forwardAllStudents(students: List<Students>) {
        // Create a list of tasks to forward all students
        val tasks = students.map { student ->
            forwardAndDeleteStudent(student)
        }
    }

    fun uploadImage(uri: Uri, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        val imageRef = firebaseStorage.child("student_images/${uri.lastPathSegment}")
        imageRef.putFile(uri)
            .addOnSuccessListener { taskSnapshot ->
                // Get the download URL of the uploaded image
                imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    onSuccess(downloadUri.toString()) // Return the image URL
                }.addOnFailureListener {
                    onError("Failed to retrieve image URL")
                }
            }
            .addOnFailureListener {
                onError("Failed to upload image: ${it.message}")
            }
    }



}