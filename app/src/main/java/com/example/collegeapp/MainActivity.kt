package com.example.collegeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.collegeapp.common.NavScreen
import com.example.collegeapp.common.theme.CollegeAppTheme
import com.example.collegeapp.jointdirector.defaulter.StudentListWithSearchJD
import com.example.collegeapp.warden.dashboard.DashboardScreen
import com.example.collegeapp.warden.defaulter.AddStudentScreen
import com.example.collegeapp.warden.defaulter.StudentListWithSearch
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CollegeAppTheme {
                //StudentListWithSearchJD()
                val navController = rememberNavController()
               NavScreen()
                //CreateAccountScreen( navController)
              // StudentListWithSearch()
               // AddStudentScreen()
                //DashboardScreen(navController = navController)
            }
        }
    }
}