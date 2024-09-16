package com.example.collegeapp.common


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.collegeapp.warden.dashboard.DashboardScreen
import com.example.collegeapp.warden.dashboard.DefaulterListButton
import com.example.collegeapp.warden.defaulter.StudentListWithSearch
import com.example.collegeapp.warden.login.LoginScreen
import com.example.collegeapp.warden.profile.ProfileScreen
import com.example.collegeapp.warden.signup.CreateAccountScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavScreen(){
    val navController = rememberNavController()
    val currentUser = FirebaseAuth.getInstance().currentUser
    val start = if (currentUser != null) "dashboard" else "login"
    NavHost(navController = navController, startDestination = start ) {
        composable("login"){
            LoginScreen(navController)
        }
        composable("create"){
            CreateAccountScreen(navController)
        }
        composable("dashboard"){
            DashboardScreen(navController)
        }
        composable("defaulterlist"){
            StudentListWithSearch(navController)
        }
        composable("profile"){
            ProfileScreen(navController)
        }
        composable("bottombar"){
            BottomBar(navController = navController)
        }

    }
}