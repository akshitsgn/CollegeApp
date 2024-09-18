package com.example.collegeapp.warden.defaulter

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.collegeapp.R
import com.example.collegeapp.common.BottomBar
import com.example.collegeapp.common.model.Students
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        placeholder = { Text("Search student") },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        }
    )
}

@Composable
fun StudentListWithSearch(navController: NavController
) {
    var isDialogOpen by remember {
        mutableStateOf(false)
    }
    var isDialogOpenR by remember {
        mutableStateOf(false)
    }

    var isDialogOpenAll by remember{
        mutableStateOf(false)
    }

    var selectedStudent by remember { mutableStateOf<Students?>(null) }
    var selectedStudentR by remember {mutableStateOf<Students?>(null)}


    val viewModel: HomeViewModel = hiltViewModel()
    val students = viewModel.users.collectAsState()


    val context = LocalContext.current
    var query by remember { mutableStateOf("") }

    // Get the current date
    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val currentDate = dateFormat.format(Date())

    if (isDialogOpen && selectedStudent != null) {
        AlertDialog(
            onDismissRequest = {
                isDialogOpen=false
            },
            text = {
                Text(
                    text = "Are you sure you want to forward the name?",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        selectedStudent?.let { student ->
                            viewModel.forwardAndDeleteStudent(student)
                        }
                        isDialogOpen = false
                        selectedStudent = null
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1DA736), // Green button background
                        contentColor = Color.White // Text color for the button
                    ),
                    modifier = Modifier
                        .height(48.dp)
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth(), // Button width proportion
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Forward")
                }
            },
            containerColor = Color.White, // Set dialog background to white
            shape = RoundedCornerShape(16.dp), // Rounded corners for the dialog box
        )
    }
    if (isDialogOpenAll ){
        AlertDialog(
            onDismissRequest = {
                isDialogOpenAll=false
            },
            text = {
                Text(
                    text = "Are you sure you want to forward all the names?",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                       viewModel.forwardAllStudents(students.value)
                        isDialogOpenAll = false

                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1DA736), // Green button background
                        contentColor = Color.White // Text color for the button
                    ),
                    modifier = Modifier
                        .height(48.dp)
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth(), // Button width proportion
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Forward")
                }
            },
            containerColor = Color.White, // Set dialog background to white
            shape = RoundedCornerShape(16.dp), // Rounded corners for the dialog box
        )
    }
    if (isDialogOpenR && selectedStudentR != null){
        AlertDialog(
            onDismissRequest = {
                isDialogOpenR=false
            },
            text = {
                Text(
                    text = "Are you sure you want to remove the name?",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        selectedStudentR?.let { student ->
                            viewModel.deleteStudent(student)
                        }
                        isDialogOpenR = false
                        selectedStudentR = null
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1DA736), // Green button background
                        contentColor = Color.White // Text color for the button
                    ),
                    modifier = Modifier
                        .height(48.dp)
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth(), // Button width proportion
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Remove")
                }
            },
            containerColor = Color.White, // Set dialog background to white
            shape = RoundedCornerShape(16.dp), // Rounded corners for the dialog box
        )
    }
    Column(
        modifier = Modifier.fillMaxSize().padding(top=26.dp)
    ) {
        // Row for Title and Date
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Defaulter List",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = currentDate,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
        }

        // Add SearchBar
        SearchBar(
            query = query,
            onQueryChange = { query = it }
        )

        // Student list
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(students.value){ student->
                StudentListItem(student = student,
                    onItemClickF = {
                        isDialogOpen=true
                        selectedStudent = student

                    },
                    onItemClickR = {
                        isDialogOpenR=true
                        selectedStudentR = student
                    })
            }
            
        }

        // Forward All button at the bottom
        Button(
            onClick = {
                isDialogOpenAll=true
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF029135), // Custom green color
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = "Forward All", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        BottomBar(navController = navController)
    }
}

@Composable
fun StudentListItem(
    student: Students,
    onItemClickF:(Students)-> Unit,
    onItemClickR: (Students) -> Unit
) {
    // Custom Colors
    val greenColor = Color(0xFF029135) // Custom green color
    val redColor = Color(0xFFDC0404)   // Custom red color
    val backgroundColor = Color(0xFFFFFFFF) // The color you asked for

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .clickable(onClick = {}), // Make the card clickable
        shape = RoundedCornerShape(12.dp), // Rounded corners for the card
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor) // Set the background color here
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            // Row for student image and information
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sajal), // Replace with actual image resource
                    contentDescription = "Student Image",
                    contentScale = ContentScale.Crop, // Ensures image fills the circle without distortion
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape) // Circular shape for the image
                )


                Spacer(modifier = Modifier.width(8.dp))

                // Student name and registration number
                Column(
                    modifier = Modifier.weight(1f) // Takes up remaining space
                ) {
                    Row {
                        Text(
                            text = "${student.name} ", // Replace with student name
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if(student.hosteler){"(${student.hostel})"} else{"(Day-Hosteler)"}, // Replace with student name
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "Reg No- ${student.regNumber}", // Replace with student registration number
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // Row for Hosteller/Day Scholar button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        onItemClickF(student)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (student.hosteler) Color.Blue else greenColor,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .height(32.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = if (student.hosteler) "Forward" else "Forward")
                }

                // Remove Button
                Button(
                    onClick = {
                        onItemClickR(student)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = redColor,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .height(32.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = "Remove")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStudentListWithSearch() {
    // Dummy data for students
//    val students = listOf(
//        Students(name = "Sajal Kumar Jana", regNumber = "230001", isHosteler = false, hostel = "A-Block", inTime = "10:00 PM"),
//        Students(name = "Ravi Sharma", regNumber = "230002", isHosteler = true, hostel = "B-Block", inTime = "09:30 PM"),
//        Students(name = "Anjali Singh", regNumber = "230003", isHosteler = false, hostel = "C-Block", inTime = "10:15 PM"),
//        Students(name = "Rahul Mehta", regNumber = "230004", isHosteler = true, hostel = "D-Block", inTime = "08:45 PM"),
//        Students(name = "Priya Agarwal", regNumber = "230005", isHosteler = false, hostel = "E-Block", inTime = "11:00 PM")
//    )

//    StudentListWithSearch(
//        students = students,
//        onClick = { /* Handle click */ },
//        onRemove = { /* Handle remove */ },
//        onForwardAll = { /* Handle forward all action */ }
//    )
}
