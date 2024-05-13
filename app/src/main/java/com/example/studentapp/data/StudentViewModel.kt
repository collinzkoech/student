package com.example.studentapp.data

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavHostController
import com.example.studentapp.models.Student
import com.example.studentapp.navigation.LOGIN_URL
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class StudentViewModel(var navController:NavHostController, var context: Context) {
    var authViewModel:AuthViewModel
    var progress:ProgressDialog
    init {
        authViewModel = AuthViewModel(navController, context)
        if (!authViewModel.isLoggedIn()){
            navController.navigate(LOGIN_URL)
        }
        progress = ProgressDialog(context)
        progress.setTitle("Loading")
        progress.setMessage("Please wait...")
    }

    fun uploadStudent(name:String, course:String, fee:String, filePath:Uri){
        val studentId = System.currentTimeMillis().toString()
        val storageRef = FirebaseStorage.getInstance().getReference()
            .child("Students/$studentId")
        progress.show()
        storageRef.putFile(filePath).addOnCompleteListener{
            progress.dismiss()
            if (it.isSuccessful){
                // Save data to db
                storageRef.downloadUrl.addOnSuccessListener {
                    val imageUrl = it.toString()
                    val student = Student(name, course, fee ,imageUrl,studentId)
                    val databaseRef = FirebaseDatabase.getInstance().getReference()
                        .child("Students/$studentId")
                    databaseRef.setValue(student).addOnCompleteListener {
                        if (it.isSuccessful){
                            Toast.makeText(this.context, "Success", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this.context, "Error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }else{
                Toast.makeText(this.context, "Upload error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun allStudents(
        student:MutableState<Student>,
        students:SnapshotStateList<Student>):SnapshotStateList<Student>{
        progress.show()
        val ref = FirebaseDatabase.getInstance().getReference()
            .child("Students")
        ref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                students.clear()
                for (snap in snapshot.children){
                    val retrievedStudent = snap.getValue(Student::class.java)
                    student.value = retrievedStudent!!
                    students.add(retrievedStudent)
                }
                progress.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "DB locked", Toast.LENGTH_SHORT).show()
            }
        })
        return students
    }

    fun deleteStudent(studentId:String){
        val ref = FirebaseDatabase.getInstance().getReference()
            .child("Students/$studentId")
        ref.removeValue()
        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
    }
}