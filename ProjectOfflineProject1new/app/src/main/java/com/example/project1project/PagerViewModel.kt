package com.example.project1project


import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.project1project.data.MessageRepository
import com.example.project1project.security.CryptoUtils
import java.util.UUID

class PagerViewModel : ViewModel() {

    //Repository instance
    private val repository = MessageRepository()

    //  State list Observed by UI
    val messages = mutableStateListOf<PagerMessage>()

    init{
        //Load existing messages (future-proof)
        messages.addAll(repository.getMessages())
    }

    // Called when user sends a message
    fun sendMessage(text: String) {
        repository.send(text)
        refreshMessages()
    }

    // Simulates receiving message from another phone
    fun receiveMessage(text: String) {
        repository.receive(text)
        refreshMessages()
    }
    // Decrypt message Only when ui needs to display it
    fun getDisplayText(encryptedText:String):String{
        return CryptoUtils.decrypt(encryptedText)
    }
    //keeps ui state in sync
    private fun refreshMessages()
    {
        messages.clear()
        messages.addAll(repository.getMessages())
    }

    // ---------------------------------------------------------
    // Simulates receiving a message from another device
    // Message arrives ALREADY ENCRYPTED (realistic scenario)
    // ---------------------------------------------------------
    fun simulateIncomingMessage(plainText:String){
        //Simulate sender-side encryption (Devide B)
        val encryptedText= CryptoUtils.encrypt(plainText)

        //Receiver only gets encrypted text
        repository.receive(encryptedText)

        //Update UI state
        messages.clear()
        messages.addAll(repository.getMessages())

    }


}

