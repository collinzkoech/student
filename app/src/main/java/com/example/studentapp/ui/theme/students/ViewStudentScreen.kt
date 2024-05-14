package com.example.studentapp.ui.theme.students
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.studentapp.R
import com.example.studentapp.data.StudentViewModel
import com.example.studentapp.models.Student
import com.example.studentapp.ui.theme.StudentAppTheme

@Composable
fun ViewStudentsScreen(navController:NavHostController) {
    Column(modifier = Modifier
        .paint(
            painterResource(id = R.drawable.image),
            contentScale = ContentScale.FillBounds
        )
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {

        var context = LocalContext.current
        var studentRepository = StudentViewModel(navController, context)


        val emptyStudentState = remember { mutableStateOf(Student("","","","","")) }
        var emptyStudentsListState = remember { mutableStateListOf<Student>() }

        var students = studentRepository.allStudents(emptyStudentState, emptyStudentsListState)


        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "All Students",
                fontSize = 30.sp,
                fontFamily = FontFamily.Serif,
                color = Color.Red)

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(){
                items(students){
                    StudentItem(
                        name = it.name,
                        course = it.course,
                        duration = it.duration,
                        id = it.id,
                        navController = navController,
                        studentRepository = studentRepository,
                        studentImage = it.imageUrl
                    )
                }
            }
        }
    }
}


@Composable
fun StudentItem(name:String, course:String, duration:String, id:String,
                navController:NavHostController,
                studentRepository:StudentViewModel, studentImage:String) {

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = name)
        Text(text = course)
        Text(text = duration)
        Image(
            painter = rememberAsyncImagePainter(studentImage),
            contentDescription = null,
            modifier = Modifier.size(250.dp)
        )
        Button(onClick = {
            studentRepository.deleteStudent(id)
        }) {
            Text(text = "Delete")
        }
        Button(onClick = {
            //navController.navigate(ROUTE_UPDATE_PRODUCTS+"/$id")
        }) {
            Text(text = "Update")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ViewStudentScreenPreview(){
    StudentAppTheme {
        ViewStudentsScreen(navController = rememberNavController())
    }
}
