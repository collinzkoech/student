
@file:Suppress("DEPRECATION")

package com.example.studentapp.ui.theme.students


import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.studentapp.ui.theme.StudentAppTheme
import com.example.studentapp.R
import com.example.studentapp.data.StudentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddStudentsScreen(navController:NavHostController){
    Column(
        modifier = Modifier
            .paint(
                painterResource(id = R.drawable.image),
                contentScale = ContentScale.FillBounds
            )
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add Students",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Cursive
        )

        var studentName by remember { mutableStateOf("") }
        var studentCourse by remember { mutableStateOf("") }
        var studentDuration by remember { mutableStateOf("") }
        val context = LocalContext.current

        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(
            value = studentName,
            onValueChange = { studentName = it },
            label = { Text(text = "Student Name *") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = studentCourse,
            onValueChange = { studentCourse = it },
            label = { Text(text = "Student Course *") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = studentDuration,
            onValueChange = { studentDuration = it },
            label = { Text(text = "Course Duration *") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.height(20.dp))



        //---------------------IMAGE PICKER START-----------------------------------//

        var modifier = Modifier
        ImagePicker(modifier,context, navController, studentName.trim(), studentCourse.trim(), studentDuration.trim())

        //---------------------IMAGE PICKER END-----------------------------------//



    }
}

@Composable
fun ImagePicker(modifier: Modifier = Modifier, context: Context,navController: NavHostController, name:String, course:String, duration:String) {
    var hasImage by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            hasImage = uri != null
            imageUri = uri
        }
    )


    Box(

        modifier = Modifier
            .width(200.dp)
            .height(250.dp)
            .clip(RoundedCornerShape(15.dp)),

        ) {


        Column(modifier = modifier) {
            if (hasImage && imageUri != null) {
                val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
                Image(bitmap = bitmap.asImageBitmap(),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Selected image")
            }


        }


    }

    Spacer(
        modifier = Modifier
            .width(30.dp)
            .height(30.dp)
    )

    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
    )

    {


        Image(
            painter = painterResource(id = R.drawable.gallery),
            modifier = Modifier
                .size(40.dp)
                .padding(start = 0.dp)
                .clickable { imagePicker.launch("image/*") } ,
            contentDescription = "picked image",
            contentScale = ContentScale.Crop,


            )

        Button(
            onClick = {
                //-----------WRITE THE UPLOAD LOGIC HERE---------------//
                val studentRepository = StudentViewModel(navController, context)
                studentRepository.uploadStudent(name, course, duration, imageUri!!)

            },
            modifier = Modifier
                .padding(start = 10.dp),

            colors = ButtonDefaults.buttonColors(
                Color.Blue
            )

        )
        {
            Text(text = "Upload")
        }
    }

}

@Composable
@Preview(showBackground = true)
fun AddStudentsScreenPreview(){
    StudentAppTheme {
        AddStudentsScreen(navController = rememberNavController())
    }
}