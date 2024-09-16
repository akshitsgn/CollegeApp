package com.example.collegeapp.common


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController


@Composable
fun BottomBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier.height(64.dp)
        ,
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 8.dp // Adds elevation for better visibility
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly // Ensures even spacing between icons
        ) {
            BottomBarButton(
                icon = Icons.Filled.Home,
                label = "Home",
                onClick = { navController.navigate("dashboard") }
            )
            BottomBarButton(
                icon = Icons.Filled.List,
                label = "Reports",

                onClick = { navController.navigate("defaulterlist") }
            )
            BottomBarButton(
                //
                icon = Icons.Filled.Person,
                label = "Profile",
                onClick = { navController.navigate("profile") }
            )
        }
    }
}

@Composable
fun BottomBarButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(48.dp) // Set uniform size for the button area
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, // Center the icon and text
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = icon,
                tint = Color(0xFF9AB7FF),
                contentDescription = label,
                modifier = Modifier.size(24.dp) // Icon size
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomBar() {
    val navController = rememberNavController() // Mock NavController for preview
    BottomBar(navController = navController)
}