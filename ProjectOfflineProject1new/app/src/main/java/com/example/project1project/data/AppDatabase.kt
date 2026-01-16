package com.example.project1project.data

import  android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.project1project.data.dao.MessageDao
import com.example.project1project.data.entity.EncryptedMessageEntity

@Database(
    entities=[EncryptedMessageEntity::class],
    version=1,
    exportSchema=false
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun messageDao() : MessageDao
    companion object{
        @Volatile private var INSTANCE : AppDatabase?=null

        fun get(context:Context):AppDatabase=
            INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,AppDatabase::class.java,
                    "campus_pager.db"
                ).build().also{
                    INSTANCE = it
                }
            }
    }
}
