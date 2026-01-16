package com.example.project1project.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="messages")
data class EncryptedMessageEntity(
    @PrimaryKey val id: String,
    val encryptedText:String,
    val timestamp:Long
)