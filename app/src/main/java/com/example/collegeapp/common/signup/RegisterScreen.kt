package com.example.collegeapp.common.signup


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.collegeapp.R
import com.google.firebase.auth.FirebaseAuth

@Composable
fun CreateAccountScreen(navController: NavController) {

    val viewModel: SignUpViewModel = hiltViewModel()
    val uiState = viewModel.state.collectAsState()

    val userRole by viewModel.userRole.collectAsState() // Observe the user role state
    val currentUser = FirebaseAuth.getInstance().currentUser

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var passwordVisible1 by remember { mutableStateOf(false) }

    var selectedRole by remember { mutableStateOf("") } // Default role selection

    val roles = listOf( "Admin", "Warden") // List of roles

    val visualTransformation: VisualTransformation =
        if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
    val visualTransformation1: VisualTransformation =
        if (passwordVisible1) VisualTransformation.None else PasswordVisualTransformation()
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val logoSize = when {
        screenWidth < 360.dp -> 80.dp
        screenWidth in 360.dp..600.dp -> 130.dp
        else -> 180.dp
    }

    val bodyImageAspectRatio = 3f / 6f
    val bodyImageHeight = when {
        screenWidth < 360.dp -> 210.dp
        screenWidth in 360.dp..600.dp -> 300.dp
        else -> 400.dp
    }

    val cardWidth = when {
        screenWidth < 360.dp -> screenWidth * 0.9f
        screenWidth in 360.dp..600.dp -> screenWidth * 0.8f
        else -> screenWidth * 0.7f
    }

    val cardHeight = 300.dp
    val cardColor = Color.White

    LaunchedEffect(currentUser?.uid) {
        currentUser?.uid?.let { uid ->
            viewModel.fetchUserRole(uid) // Fetch user role for the logged-in user
        }
    }
    Log.d("userrole",userRole.toString())

    val context = LocalContext.current
    LaunchedEffect(key1 = uiState.value) {
        when (uiState.value) {
            is SignUpState.Success -> {
                if(userRole=="Warden"){
                navController.navigate("dashboard")
                    }
                else{
                    navController.navigate("dashboardJD")
                }
            }
            is SignUpState.Error -> {
                Toast.makeText(context, "Sign Up Failed", Toast.LENGTH_LONG).show()
            }
            else -> {

            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 12.dp),
        verticalArrangement = Arrangement.Top
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.aitlogo),
            contentDescription = "Logo",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(logoSize)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Image(
                painter = painterResource(id = R.drawable.exit),
                contentDescription = "Body Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(bodyImageHeight)
                    .padding(vertical = 16.dp)
                    .aspectRatio(bodyImageAspectRatio)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Card containing the form
            Card(
                modifier = Modifier
                    .offset(y = (-cardHeight / 3))
                    .width(cardWidth)
                    .align(Alignment.CenterHorizontally)
                    .shadow(8.dp, shape = RoundedCornerShape(18.dp))
                    .background(cardColor),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Register Text
                    Text(
                        "REGISTER",
                        style = TextStyle(
                            fontFamily = FontFamily.Serif,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Blue
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // E-Mail Field
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = {
                            Text(
                                "E-Mail",
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp
                                )
                            )
                        },
                        colors = androidx.compose.material3.TextFieldDefaults.colors(
                            focusedIndicatorColor = Color(0xFF029135),
                            unfocusedIndicatorColor = Color(0xFF029135)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )

                    // Password Field
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        isError = password.isNotEmpty() &&
                                confirmPassword.isNotEmpty() && password != confirmPassword,
                        visualTransformation = visualTransformation,
                        trailingIcon = {
                            val icon = if (passwordVisible) Icons.Filled.Lock else Icons.Filled.Lock
                            Icon(
                                imageVector = icon,
                                contentDescription = if (passwordVisible) "Hide password" else "Show password",
                                modifier = Modifier.clickable { passwordVisible = !passwordVisible }
                            )
                        },
                        label = {
                            Text(
                                "Password",
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp
                                )
                            )
                        },
                        colors = androidx.compose.material3.TextFieldDefaults.colors(
                            focusedIndicatorColor = Color(0xFF029135),
                            unfocusedIndicatorColor = Color(0xFF029135)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )

                    // Confirm Password Field
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        visualTransformation = visualTransformation1,
                        trailingIcon = {
                            val icon = if (passwordVisible1) Icons.Filled.Lock else Icons.Filled.Lock
                            Icon(
                                imageVector = icon,
                                contentDescription = if (passwordVisible1) "Hide password" else "Show password",
                                modifier = Modifier.clickable { passwordVisible1 = !passwordVisible1 }
                            )
                        },
                        label = {
                            Text(
                                "Confirm Password",
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp
                                )
                            )
                        },
                        colors = androidx.compose.material3.TextFieldDefaults.colors(
                            focusedIndicatorColor = Color(0xFF029135),
                            unfocusedIndicatorColor = Color(0xFF029135)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // User Role Selection
                    Text(
                        "Select Role:",
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 17.sp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Row for Radio Buttons for User Role
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        roles.forEach { role ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clickable { selectedRole = role }
                            ) {
                                androidx.compose.material3.RadioButton(
                                    selected = (selectedRole == role),
                                    onClick = { selectedRole = role }
                                )
                                Text(
                                    text = role,
                                    style = TextStyle(fontSize = 16.sp),
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (uiState.value == SignUpState.Loading) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    } else {
                        Button(
                            onClick = { viewModel.signUp(username, password, selectedRole) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF029135),
                                contentColor = Color.White
                            ),
                            enabled = username.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && password == confirmPassword && (selectedRole=="Admin" || selectedRole == "Warden"),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .width(140.dp)
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Text("Register")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Login text
                    Row(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            "Already have an account? ",
                            style = TextStyle(fontSize = 14.sp)
                        )
                        Text(
                            "Login",
                            style = TextStyle(fontSize = 14.sp),
                            color = Color(0xFF029135),
                            modifier = Modifier.clickable {
                                navController.navigate("login")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Check(){
    val navController = rememberNavController()
    CreateAccountScreen(navController = navController)
}