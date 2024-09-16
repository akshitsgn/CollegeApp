package com.example.collegeapp.jointdirector.dashboard



import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.nativeCanvas
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.collegeapp.R
import com.example.collegeapp.common.BottomBar
import com.example.collegeapp.warden.defaulter.HomeViewModel

@Composable
fun DashboardScreenJD(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(26.dp)
                .verticalScroll(rememberScrollState())
        ) {
            val labels = listOf("Hostelers", "Students", "Visitors", "Others")
            val colors =
                listOf(Color(0xFFFFA800), Color(0xFF38C555), Color(0xFF3478F7), Color(0xFFF64D4D))
            HeaderSectionJD()
            Spacer(modifier = Modifier.height(16.dp))
            StatCardsSectionJD()
            Spacer(modifier = Modifier.height(24.dp))
            DefaulterListButtonJD(onClickAction = {
                navController.navigate("defaulterlistJD")
            })
            Spacer(modifier = Modifier.height(24.dp))
            CircularChartSectionJD() // This will now be properly centered
            Spacer(modifier = Modifier.height(16.dp))
        }
        BottomBar(navController = navController,
            modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun HeaderSectionJD() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "DASHBOARD",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
        )
        Image(
            painter = painterResource(id = R.drawable.jd), // Placeholder, replace with actual image
            contentDescription = "Profile",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )
    }
}

@Composable
fun StatCardsSectionJD() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val homeViewModel:HomeViewModel= hiltViewModel()
        val hostelersCount by homeViewModel.hostelersCount.collectAsState(initial = 0)
        val dayHostelersCount by homeViewModel.dayHostelersCount.collectAsState(initial = 0)
        val leaveCount by homeViewModel.leaveCount.collectAsState(initial = 0)
        val lateCount by homeViewModel.lateCount.collectAsState(initial = 0)

        val sum = hostelersCount + dayHostelersCount
        StatCardJD(label = "Late Students", count = "${lateCount}", icon = R.drawable.lateimage)
        Spacer(modifier = Modifier.width(8.dp))
        StatCardJD(label = "Hostlers", count = "${hostelersCount}", icon = R.drawable.hostelers)
    }
}

@Composable
fun StatCardJD(label: String, count: String, icon: Int) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(100.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = icon), // Placeholder for an icon
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
            Text(text = label, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = count, fontSize = 18.sp)
        }
    }
}

@Composable
fun DefaulterListButtonJD(onClickAction:()-> Unit) {

    Row(modifier = Modifier.fillMaxWidth()){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
            , // Add padding around the card
            // elevation = 8.d, // Elevation to make the card pop
            shape = RoundedCornerShape(12.dp) // Rounded corners for a modern look
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp), // Padding inside the card
                horizontalArrangement = Arrangement.SpaceBetween, // Space between Text and Button
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Defaulter's List",
                    fontSize = 18.sp, // Increase font size for better readability
                    fontWeight = FontWeight.Bold, // Bold text for emphasis
                    color = Color(0xFF333333) // Use a dark grey color
                )

                Button(
                    onClick = {onClickAction() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF029135)),
                    shape = RoundedCornerShape(8.dp), // Rounded corners for the button
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .height(48.dp) // Fixed height for the button
                        .padding(start = 8.dp,) // Add padding between the text and button
                ) {
                    Text(text = "See List", color = Color.White, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

@Composable
fun CircularChartSectionJD() {
    val colors = listOf(Color(0xFFFFA800), Color(0xFF38C555))
    val colors1 = listOf(Color(0xFF3478F7), Color(0xFFF64D4D))
    val labels = listOf("Hostelers", "Day-\nHostelers")
    val labels1  = listOf("Leave", "Late")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .height(500.dp) // Allocate enough space for the chart
            .wrapContentSize(Alignment.Center) // Ensure everything is centered
    ) {
        val homeViewModel:HomeViewModel= hiltViewModel()
        val hostelersCount by homeViewModel.hostelersCount.collectAsState(initial = 0)
        val dayHostelersCount by homeViewModel.dayHostelersCount.collectAsState(initial = 0)
        val leaveCount by homeViewModel.leaveCount.collectAsState(initial = 0)
        val lateCount by homeViewModel.lateCount.collectAsState(initial = 0)

        val sum = hostelersCount + dayHostelersCount

        val proportions = if (sum > 0) {
            listOf(
                (hostelersCount.toFloat() / sum) * 100f,   // Hosteler proportion
                (dayHostelersCount.toFloat() / sum) * 100f, // Day Hosteler proportion
                (leaveCount.toFloat() / sum) * 100f,       // On Leave proportion
                (lateCount.toFloat() / sum) * 100f         // Late Student proportion
            )
        } else {
            listOf(0f, 0f, 0f, 0f)  // No data case
        }
        Log.d("list",proportions.toString())
        Card(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
                .padding(16.dp)// Adjust card size to properly display the chart
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(42.dp), // Use full available space
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                CircularChartJD(modifier = Modifier.size(250.dp),proportions) // Adjust the chart size for responsiveness
                Spacer(modifier = Modifier.height(24.dp)) // Space between chart and legend
                // Legend below the chart
                LegendJD(colors, labels1, colors1, labels)
            }
        }
    }
}
@Composable
fun CircularChartJD(modifier: Modifier = Modifier,proportions:List<Float>) {

    val homeViewModel:HomeViewModel= hiltViewModel()
    val hostelersCount by homeViewModel.hostelersCount.collectAsState(initial = 0)
    val dayHostelersCount by homeViewModel.dayHostelersCount.collectAsState(initial = 0)
    val leaveCount by homeViewModel.leaveCount.collectAsState(initial = 0)
    val lateCount by homeViewModel.lateCount.collectAsState(initial = 0)

    val sum = hostelersCount + dayHostelersCount
    // Define a scaling factor to enlarge the chart without affecting the original values
    val scaleFactor = 2.3f // Scaling factor remains 1x for proper sizing
    val arcStrokeWidth = 42f // Adjust the stroke width for thinner arcs

    Canvas(
        modifier = modifier//.padding(80.dp, 60.dp, 20.dp, 0.dp)
    ) {
        // Draw the circular chart segments using arcs
        val totalValue = 100f
        // val proportions = listOf(25f, 55f, 15f, 5f) // Example percentages for each category
        val colors = listOf(
            Color(0xFFFFA800),  // Orange for Hostelers
            Color(0xFF38C555),  // Green for Day Hostelers
            Color(0xFF3478F7),  // Blue for On Leave
            Color(0xFFF64D4D)
        )
        var startAngle = -90f

        val chartSize = 300f * scaleFactor // Set size of the chart

        proportions.forEachIndexed { index, proportion ->
            val sweepAngle = (proportion / totalValue) * 360f
            drawArc(
                color = colors[index],
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false, // We are using a stroke, so no center fill
                size = Size(chartSize, chartSize), // Scale the size
                style = androidx.compose.ui.graphics.drawscope.Stroke(
                    width = arcStrokeWidth * scaleFactor // Thinner arc with stroke width
                ),
                topLeft = Offset(
                    (size.width - chartSize) / 2,
                    (size.height - chartSize) / 2
                ) // Center the arc
            )
            startAngle += sweepAngle
        }
        drawContext.canvas.nativeCanvas.apply {
            drawText(
                "${sum}",
                center.x, center.y + (32f * scaleFactor),
                android.graphics.Paint().apply {
                    textSize = 49f * scaleFactor // Scale the text size
                    textAlign = android.graphics.Paint.Align.CENTER
                    color = android.graphics.Color.BLACK
                    isFakeBoldText = true
                }
            )

            drawText(
                "Total Students:",
                center.x, center.y - (26f * scaleFactor),
                android.graphics.Paint().apply {
                    textSize = 30f * scaleFactor // Scale the text size
                    textAlign = android.graphics.Paint.Align.CENTER
                    color = android.graphics.Color.BLACK
                    isFakeBoldText = true
                }
            )
        }
    }
}

@Composable
fun LegendJD(colors: List<Color>, labels1: List<String>,colors1: List<Color>, labels: List<String>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier=Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            colors.zip(labels).forEach { (color, label) ->
                Row(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .weight(1f, false),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .wrapContentWidth()
                            .size(24.dp)
                            .background(color, shape = RoundedCornerShape(4.dp))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = label, style = TextStyle(fontSize = 12.sp))
                    Spacer(modifier = Modifier.width(18.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            colors1.zip(labels1).forEach { (color, label) ->
                Row(
                    modifier = Modifier.padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(color, shape = RoundedCornerShape(4.dp))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = label, style = TextStyle(fontSize = 12.sp))
                    Spacer(modifier = Modifier.width(34.dp))
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    val navController = rememberNavController()
    DashboardScreenJD(navController)
}