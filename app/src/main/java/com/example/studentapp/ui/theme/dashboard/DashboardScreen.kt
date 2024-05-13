package com.example.studentapp.ui.theme.dashboard



import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.studentapp.R
import com.example.studentapp.ui.theme.StudentAppTheme
import com.example.studentapp.navigation.ADD_STUDENTS_URL
import com.example.studentapp.navigation.VIEW_STUDENTS_URL

@Composable
fun DashboardScreen(navController:NavHostController){
    Column(
        modifier = Modifier
            .paint(painterResource(id = R.drawable.students), contentScale = ContentScale.FillBounds)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Student App",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )
        Spacer(modifier = Modifier.height(150.dp))
        Button(onClick = {
            navController.navigate(ADD_STUDENTS_URL)
        }) {
            Text(text = "Add Student",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif)
        }
        Spacer(modifier = Modifier.height(50.dp))
        Button(onClick = {
            navController.navigate(VIEW_STUDENTS_URL)
        }) {
            Text(text = "View Student",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif)
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun DashboardScreenPreview(){
    StudentAppTheme {
        DashboardScreen(navController = rememberNavController())
    }
}