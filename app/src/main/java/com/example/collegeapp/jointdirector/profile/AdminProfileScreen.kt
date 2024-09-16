package com.example.collegeapp.jointdirector.profile



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.collegeapp.R
import com.example.collegeapp.common.BottomBar
import com.example.collegeapp.common.login.SignInViewModel

@Composable
fun ProfileScreenJD(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7)), // Background color
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top section with cover and profile image
        TopSectionJD()

        Spacer(modifier = Modifier.height(16.dp))

        // Profile Name and ID
        Text("Admin", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("ID NUMBER: 52432534242", fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(24.dp))
        // Action Buttons
        ProfileButtonSectionJD(navController)
        Spacer(modifier = Modifier.weight(1f))
        // Footer section with logo
        FooterSectionJD()
        BottomBar(navController = navController)
    }
}

@Composable
fun TopSectionJD() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(
                color = Color(0xFF3D7FFF).copy(alpha = 0.1f), // Light blue background
                shape = RoundedCornerShape(bottomStart = 90.dp, bottomEnd = 90.dp)
            ),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Profile image within the circle
        Image(
            painter = painterResource(id = R.drawable.jd),// Placeholder for the profile image
            contentDescription = "Profile Image",
            modifier = Modifier

                .size(120.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(4.dp, Color.White, CircleShape)
                .clickable {  },
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun ProfileButtonSectionJD(navController: NavController) {
    val viewmodel: SignInViewModel = hiltViewModel()
    Column {
        ProfileActionButtonJD(iconId = R.drawable.erp, label = "ERP") {
            // Handle ERP click
        }
        ProfileActionButtonJD(iconId = R.drawable.edit, label = "Edit Profile") {
            // Handle Edit Profile click
        }
        ProfileActionButtonJD(iconId = R.drawable.setting, label = "Settings") {
            // Handle Settings click
        }
        ProfileActionButtonJD(iconId = R.drawable.logout, label = "Logout", isDanger = true) {
            viewmodel.signOut()
            navController.navigate("login")
        }
    }
}

@Composable
fun ProfileActionButtonJD(iconId: Int, label: String, isDanger: Boolean = false, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor =  Color.White, // Red for danger, white otherwise
            contentColor = if (isDanger) Color.Red else Color.Black
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(vertical = 8.dp)
            .height(60.dp)
            .border(
                width = 1.dp, // Border thickness
                color = Color(0xFF1B59F8), // Blue outline
                shape = RoundedCornerShape(8.dp) // Same shape as the button
            ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start, // Align items at the start of the button
            modifier = Modifier.fillMaxWidth() // Ensure the Row takes up the full width
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = label,
                tint = if (isDanger) Color.Red else Color.Black,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = label, fontSize = 18.sp)
        }
    }
}


@Composable
fun FooterSectionJD() {
    Image(
        painter = painterResource(id = R.drawable.aitbluelogo), // Placeholder for logo
        contentDescription = "Logo",
        modifier = Modifier
            .size(80.dp)
            .padding(bottom = 16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen1() {
    val navController= rememberNavController()
    ProfileScreenJD(navController)
}
