package com.example.studentapp.models
class Student {
    var name:String = ""
    var course:String = ""
    var duration:String = ""
    var imageUrl:String = ""
    var id:String = ""

    constructor(name: String, quantity: String, price: String, imageUrl: String, id: String) {
        this.name = name
        this.course = quantity
        this.duration = price
        this.imageUrl = imageUrl
        this.id = id
    }

    constructor()
}