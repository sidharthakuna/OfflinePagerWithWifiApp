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

    suspend fun getMessages(): List<PagerMessage> =
        withContext(Dispatchers.IO) {
            messageDao.getAll().map {
                PagerMessage(
                    id = it.id,
                    text = CryptoUtils.decrypt(it.encryptedText),
                    timestamp = it.timestamp,
                    type = it.type,
                    senderPagerId = it.senderPagerId,
                    receiverPagerId = it.receiverPagerId   // ✅ FIX
                )
            }
        }


    //Load messages from DB (decrypt for UI)
    suspend fun send(text: String, myPagerId: String,receiverPagerId:String) =
        withContext(Dispatchers.IO) {
            val encryptedText = CryptoUtils.encrypt(text)

            messageDao.insert(
                EncryptedMessageEntity(
                    id = UUID.randomUUID().toString(),
                    encryptedText = encryptedText,
                    timestamp = System.currentTimeMillis(),
                    type = MessageType.SENT,
                    senderPagerId = myPagerId,   // ✅ FIX
                    receiverPagerId = receiverPagerId
                )
            )

            messageDao.keepLastMessages(500)
        }


    //Receive message (already encrypted)
    suspend fun receive(encryptedText: String, senderPagerId: String) =
        withContext(Dispatchers.IO) {
            messageDao.insert(
                EncryptedMessageEntity(
                    id = UUID.randomUUID().toString(),
                    encryptedText = encryptedText,
                    timestamp = System.currentTimeMillis(),
                    type = MessageType.RECEIVED,
                    senderPagerId = senderPagerId,
                    receiverPagerId = null   // ✅ IMPORTANT
                )
            )

            //LIMIT STORAGE
            messageDao.keepLastMessages(500)
        }

    //Clear AllMessages
    suspend fun clearAllMessages() =
        withContext(Dispatchers.IO){
            messageDao.deleteAllMessages()
        }
    suspend fun cleanupOldMessages(limit:Int =500)=
        withContext(Dispatchers.IO){
            messageDao.keepLastMessages(limit)
        }
}