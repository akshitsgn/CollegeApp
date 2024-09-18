package com.example.collegeapp.common.login


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.widthIn
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
import com.example.collegeapp.common.signup.SignUpViewModel
import com.google.firebase.auth.FirebaseAuth


@Composable
fun LoginScreen(navController: NavController) {
  val viewModel1: SignUpViewModel= hiltViewModel()
    val viewModel: SignInViewModel = hiltViewModel()
    val uiState = viewModel.state.collectAsState()

    val userRole by viewModel1.userRole.collectAsState() // Observe the user role state
    val currentUser = FirebaseAuth.getInstance().currentUser

    LaunchedEffect(currentUser?.uid) {
        currentUser?.uid?.let { uid ->
            viewModel1.fetchUserRole(uid) // Fetch user role for the logged-in user
        }
    }

    var isLoading by remember { mutableStateOf(false) }

    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    val (passwordVisible, setPasswordVisible) = remember { mutableStateOf(false) }


    // Toggle visual transformation based on password visibility
    val visualTransformation: VisualTransformation =
        if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()



    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp


    val logoSize = when {
        screenWidth < 360.dp -> 80.dp
        screenWidth in 360.dp..600.dp -> 130.dp
        else -> 180.dp
    }

    val bodyImageAspectRatio = 3f / 6f // Example aspect ratio for the body image
    val bodyImageHeight = when {
        screenWidth < 360.dp -> 210.dp
        screenWidth in 360.dp..600.dp -> 300.dp
        else -> 400.dp
    }
    val cardWidth = when {
        screenWidth < 360.dp -> screenWidth * 0.85f // 80% of screen width for small screens
        screenWidth in 360.dp..600.dp -> screenWidth * 0.9f // 75% of screen width for medium screens
        else -> screenWidth * 0.85f // 70% of screen width for large screens
    }
    val cardHeight = 300.dp
    val cardColor = Color.White

    val context = LocalContext.current
    LaunchedEffect(key1 = uiState.value) {
        when(uiState.value){
            is SignInState.Success ->{
                Toast.makeText(context,"Sign In Success",Toast.LENGTH_LONG).show()
            }
            is SignInState.Error ->{
                Toast.makeText(context,"Sign In Failed",Toast.LENGTH_LONG).show()
            }
            else->{

            }
        }
    }

    LaunchedEffect(userRole) {
        if (userRole!=null) {
            isLoading = false
            if (userRole == "Warden") {
                navController.navigate("dashboard")
            } else {
                navController.navigate("dashboardJD")
            }
        }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    else{
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(top = 12.dp), // Padding around the edges
            verticalArrangement = Arrangement.SpaceBetween // Distribute space between elements
        ) {

            Image(
                painter = painterResource(id = R.drawable.aitlogo),
                contentDescription = "Logo",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally) // Center horizontally
                    .size(logoSize) // Responsive size
            )
            Spacer(modifier = Modifier.height(75.dp))
            Image(
                painter = painterResource(id = R.drawable.exit),
                contentDescription = "Body Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(bodyImageHeight)
                    .padding(vertical = 24.dp)
                    .aspectRatio(bodyImageAspectRatio)
            )
            Card(
                modifier = Modifier
                    .offset(y = (-cardHeight / 5))
                    .width(cardWidth)
                    .align(Alignment.CenterHorizontally)
                    .shadow(42.dp, shape = RoundedCornerShape(18.dp))
                    .background(cardColor)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        "LOGIN",
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
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        colors = androidx.compose.material3.TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedIndicatorColor = Color(0xFF029135)
                        ),
                        label = {
                            Text(
                                "Username",
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp
                                )
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                        },
                        colors = androidx.compose.material3.TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedIndicatorColor = Color(0xFF029135)
                        ),
                        visualTransformation = visualTransformation,
                        trailingIcon = {
                            val icon = if (passwordVisible) Icons.Filled.Lock else Icons.Filled.Lock
                            Icon(
                                imageVector = icon,
                                contentDescription = if (passwordVisible) "Hide password" else "Show password",
                                modifier = Modifier.clickable {
                                    setPasswordVisible(!passwordVisible)
                                }
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                            .widthIn(min = 150.dp, max = 400.dp)
                    )
                    if (uiState.value == SignInState.Loading) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    } else {
                        Button(
                            onClick = { viewModel.signIn(username, password) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF029135), // Background color of the button
                                contentColor = Color.White // Text color of the button
                            ),
                            enabled = username.isNotEmpty() && password.isNotEmpty() && (uiState.value == SignInState.Nothing || uiState.value == SignInState.Error),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .width(140.dp)
                                .align(Alignment.CenterHorizontally) // Center the button horizontally
                            // Optional padding above the button
                        ) {
                            Text("Log In")
                        }
                    }
                    Text("Forgot Password?",
                        style = TextStyle(
                            fontSize = 14.sp
                        ),

                        color = Color(0xFF029135),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .clickable {

                            })
                    Spacer(modifier = Modifier.height(25.dp))
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        Text(
                            "Don't have an account? ",
                            style = TextStyle(
                                fontSize = 14.sp
                            )
                        )
                        Text("Register",
                            style = TextStyle(
                                fontSize = 14.sp
                            ),
                            color = Color(0xFF029135),
                            modifier = Modifier.clickable {
                                navController.navigate("create")
                            })
                    }
                }

            }
        }
    }
}





@Preview(showBackground = true )
@Composable
fun Check(){
    val navController = rememberNavController()
    LoginScreen(navController)
}