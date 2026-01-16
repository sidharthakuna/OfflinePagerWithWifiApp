package com.example.project1project.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
 * MessageEntity
 * --------------
 * This class represents ONE ROW in the Room database.
 *
 * IMPORTANT:
 * - This is NOT the same as PagerMessage
 * - This is ONLY for storage (database layer)
 * - Text stored here is ALWAYS ENCRYPTED
 */
@Entity(
    tableName="messages" //name of the database table
)
data class MessageEntity(
    @PrimaryKey
    val id:String,

    val encryptedText:String,

    val timestamp: Long,

    val type:String
)