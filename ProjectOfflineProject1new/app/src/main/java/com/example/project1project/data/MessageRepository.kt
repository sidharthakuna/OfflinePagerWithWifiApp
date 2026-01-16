package com.example.project1project.data

import com.example.project1project.MessageType
import com.example.project1project.PagerMessage
import com.example.project1project.data.dao.MessageDao
import com.example.project1project.data.entity.EncryptedMessageEntity
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers

import java.util.UUID

////--------------Import for the encryption and decoding the message ---------------
import com.example.project1project.security.CryptoUtils

class MessageRepository(
    private val messageDao : MessageDao
){

    //Load messages from DB (decrypt for UI)
    suspend fun getMessages():List<PagerMessage> =
        withContext(Dispatchers.IO){
            messageDao.getAll().map{
                PagerMessage(
                    id=it.id,
                    text=CryptoUtils.decrypt(it.encryptedText),
                    timestamp=it.timestamp,
                    type=MessageType.SENT  //LOCAL MESSAGES
                )
            }
        }
    // Send messages (encrypt -> save )
    suspend fun send(text : String)=
        withContext(Dispatchers.IO){
            val encryptedText = CryptoUtils.encrypt(text)

            messageDao.insert(
                EncryptedMessageEntity(
                    id=UUID.randomUUID().toString(),
                    encryptedText=encryptedText,
                    timestamp=System.currentTimeMillis()
                )
            )
        }
    //Receive message (already encrypted)
    suspend fun receive(encryptedText: String) =
        withContext(Dispatchers.IO) {
            messageDao.insert(
                EncryptedMessageEntity(
                    id = UUID.randomUUID().toString(),
                    encryptedText = encryptedText,
                    timestamp = System.currentTimeMillis()
                )
            )
        }
}