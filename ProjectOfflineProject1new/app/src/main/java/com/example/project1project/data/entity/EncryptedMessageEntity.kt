package com.example.project1project.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

import com.example.project1project.MessageType

@Entity(tableName="messages")
data class EncryptedMessageEntity(
    @PrimaryKey val id: String,
    val encryptedText:String,
    val timestamp:Long,
    val type : MessageType   //Sent or Receive
)