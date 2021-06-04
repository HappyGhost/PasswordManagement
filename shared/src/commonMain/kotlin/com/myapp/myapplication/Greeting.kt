package com.myapp.myapplication

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}