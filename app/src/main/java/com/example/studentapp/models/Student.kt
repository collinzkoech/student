package com.example.studentapp.models


class Student {
    var name:String = ""
    var course:String = ""
    var fee:String = ""
    var imageUrl:String = ""
    var id:String = ""

    constructor(name: String, course: String, fee: String, imageUrl: String, id: String) {
        this.name = name
        this.course = course
        this.fee = fee
        this.imageUrl = imageUrl
        this.id = id
    }
constructor()

}