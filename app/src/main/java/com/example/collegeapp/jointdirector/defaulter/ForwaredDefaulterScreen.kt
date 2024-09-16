package com.example.collegeapp.jointdirector.defaulter

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.collegeapp.R
import com.example.collegeapp.common.model.Students
import com.example.collegeapp.warden.defaulter.HomeViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SearchBarJD(
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
fun StudentListWithSearchJD(
) {
    var isDialogOpen by remember {
        mutableStateOf(false)
    }
    var isDialogOpenR by remember {
        mutableStateOf(false)
    }

    val viewModel: ForwardedDefaulterViewModel = hiltViewModel()
    val forward = viewModel.users.collectAsState()


    val context = LocalContext.current
    var query by remember { mutableStateOf("") }

    // Get the current date
    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val currentDate = dateFormat.format(Date())

    Column(
        modifier = Modifier.fillMaxSize()
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
                text = " Forwarded List",
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
        SearchBarJD(
            query = query,
            onQueryChange = { query = it }
        )

        // Student list
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
           items(forward.value){students->
               StudentListItemJD(student = students)
           }
        }

        // Forward All button at the bottom
//        Button(
//            onClick = {
//                // Show a toast when clicked
//            },
//            colors = ButtonDefaults.buttonColors(
//                containerColor = Color(0xFF029135), // Custom green color
//                contentColor = Color.White
//            ),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            shape = RoundedCornerShape(16.dp)
//        ) {
//            Text(text = "Forward All", fontSize = 18.sp, fontWeight = FontWeight.Bold)
//        }
    }
}

@Composable
fun StudentListItemJD(
    student: Students
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
                    Text(
                        text = if(student.inTime<"10:00pm"){"InTime- ${student.inTime}"}else{""}, // Replace with student registration number
                        fontSize = 14.sp,
                        color = Color.Gray
                    )


                }
            }


        }
    }
}