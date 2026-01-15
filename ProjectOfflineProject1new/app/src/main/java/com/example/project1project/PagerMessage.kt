package com.example.project1project

//Identifies wheather is SENT OR RECIEVED
enum class MessageType{
    SENT,
    RECEIVED
}

// This data class represents ONE pager message in the app
data class PagerMessage(
    // Unique ID for each message
    val id: String,

    // Actual message content typed or received
    val text: String,

    // Time when the message was created
    val timestamp: Long,
    val type: MessageType

    //NEW: MESSAGE DIRECTION
)
