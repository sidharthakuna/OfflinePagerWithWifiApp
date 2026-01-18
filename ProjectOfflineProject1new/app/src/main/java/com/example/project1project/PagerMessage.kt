package com.example.project1project

//Identifies wheather is SENT OR RECIEVED
enum class MessageType{
    SENT,
    RECEIVED
}

// This data class represents ONE pager message in the app
data class PagerMessage(
    val id: String,
    val text: String,
    val timestamp: Long,
    val type: MessageType,

    val senderPagerId: String,
    val receiverPagerId: String?   // ✅ ADD THIS
)
