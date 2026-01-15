package com.example.finalproject

//This data class represents ONE pager message in the app
data class PagerMessage(
    //Unique ID for each message
    //Used for identification,debugging, or future features
    val id: String,
    //Actual message content typed or received
    val text: String,
    //Time when the message was created(System.currentTimeMillis)
    //Useful for odering,histroy, or future timestamp display
    val timestamp: Long
)