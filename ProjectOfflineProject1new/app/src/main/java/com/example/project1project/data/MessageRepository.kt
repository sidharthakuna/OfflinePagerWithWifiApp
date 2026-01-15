package com.example.project1project.data

import com.example.project1project.MessageType
import com.example.project1project.PagerMessage
import java.util.UUID

////--------------Import for the encryption and decoding the message ---------------
import com.example.project1project.security.CryptoUtils

class MessageRepository{
    // Internal message list
    private val _messages=mutableListOf<PagerMessage>()

    //Expose read-only list
    fun getMessages(): List<PagerMessage> = _messages

    //Add sent Messages
    fun send(text:String){
        // 🔐ENCRYPT MESSAGE BEFORE SAVING
        val encryptedText= CryptoUtils.encrypt(text)

        _messages.add(
            PagerMessage(
                id=UUID.randomUUID().toString(),
                text = encryptedText,    //STORED AS ENCRYPTED
                timestamp=System.currentTimeMillis(),
                type=MessageType.SENT
            )
        )
    }
    fun receive(text:String){
        //INCOMING TEXT IS ALREADY ENCRYPTED
        _messages.add(
            PagerMessage(
                id=UUID.randomUUID().toString(),
                text=text,
                timestamp=System.currentTimeMillis(),
                type=MessageType.RECEIVED
            )
        )
    }
}