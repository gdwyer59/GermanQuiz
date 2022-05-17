package com.garethdwyer.germanquiz

class User() {
    constructor(fullName: String, age:String, email:String) : this(){
        this.fullName = fullName
        this.age = age
        this.email = email
    }

    lateinit var fullName: String
    lateinit var age: String
    lateinit var email: String
}