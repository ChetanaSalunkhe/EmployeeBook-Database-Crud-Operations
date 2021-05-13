package com.example.bateerycharge

class Employee {
    var name : String = ""
    var address :String = ""
    var mobile : String = ""
    var email : String = ""
    var designation : String = ""
    var gender : String = ""

    get() = field
    set(value){
        field = value
    }

    constructor() {}

    constructor(name: String, address: String, mobile: String, email: String, designation: String,gender:String) {
        this.name = name
        this.address = address
        this.mobile = mobile
        this.email = email
        this.designation = designation
        this.gender = gender
    }




}