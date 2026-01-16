package com.example.project1project.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.project1project.data.entity.EncryptedMessageEntity

@Dao
interface MessageDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(message: EncryptedMessageEntity)

    @Query("SELECT * FROM messages ORDER BY timestamp ASC")
    suspend fun getAll():List<EncryptedMessageEntity>

    //Delete message from chart
    @Query("DELETE FROM messages")
    suspend fun deleteAllMessages()
}
