package com.example.project1project

// This data class represents ONE pager message in the app
data class PagerMessage(
    // Unique ID for each message
    val id: String,

    // Actual message content typed or received
    val text: String,

    // Time when the message was created
    val timestamp: Long
)
