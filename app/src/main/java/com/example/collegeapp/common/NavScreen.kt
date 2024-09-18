package com.example.collegeapp.common


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.collegeapp.warden.dashboard.DashboardScreen
import com.example.collegeapp.warden.dashboard.DefaulterListButton
import com.example.collegeapp.warden.defaulter.StudentListWithSearch
import com.example.collegeapp.common.login.LoginScreen
import com.example.collegeapp.warden.profile.ProfileScreen
import com.example.collegeapp.common.signup.CreateAccountScreen
import com.example.collegeapp.common.signup.SignUpViewModel
import com.example.collegeapp.jointdirector.dashboard.DashboardScreenJD
import com.example.collegeapp.jointdirector.defaulter.StudentListWithSearchJD
import com.example.collegeapp.jointdirector.profile.ProfileScreenJD
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavScreen(){
    val navController = rememberNavController()
    val viewModel: SignUpViewModel = hiltViewModel()
    val userRole by viewModel.userRole.collectAsState() // Observe the user role
    val currentUser = FirebaseAuth.getInstance().currentUser
    LaunchedEffect(currentUser) {
        if (currentUser == null) {
            // If no user is logged in, navigate to the login screen
            navController.navigate("login") {
                popUpTo("login") { inclusive = true }
            }
        } else {
            // Fetch the user role when the user is logged in
            viewModel.fetchUserRole(currentUser.uid)
        }
    }
    var isLoading by remember { mutableStateOf(true) }
    var startDestination by remember { mutableStateOf("loading") }

    LaunchedEffect(userRole) {
        if (userRole != null) {
            isLoading = false
            startDestination = if (userRole == "Warden") {
                "dashboard"
            } else {
                "dashboardJD"
            }
        }
    }

    NavHost(navController = navController, startDestination = startDestination ) {
        composable("login"){
            LoginScreen(navController)
        }
        composable("loading") {
            if (isLoading) {
                LoadingScreen() // Show a simple loading indicator
            } else {
                LaunchedEffect(Unit) {
                    navController.navigate(startDestination) {
                        popUpTo("loading") { inclusive = true }
                    }
                }
            }
        }
        composable("create"){
            CreateAccountScreen(navController)
        }
        composable("dashboard"){
            DashboardScreen(navController)
        }
        composable("dashboardJD"){
            DashboardScreenJD(navController)
        }
        composable("defaulterlist"){
            StudentListWithSearch(navController)
        }
        composable("defaulterlistJD"){
            StudentListWithSearchJD(navController)
        }
        composable("profile"){
            ProfileScreen(navController)
        }
        composable("profileJD"){
            ProfileScreenJD(navController)
        }
        composable("bottombar"){
            BottomBar(navController = navController)
        }
    }
}@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center // Align the circular indicator to the center
    ) {
        CircularProgressIndicator()
    }
}