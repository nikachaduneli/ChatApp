package com.khvedelidze.chatapp

import java.util.*

data class Message(val text:String , val id:String, val person:String){
    constructor(): this("", "", "")
}