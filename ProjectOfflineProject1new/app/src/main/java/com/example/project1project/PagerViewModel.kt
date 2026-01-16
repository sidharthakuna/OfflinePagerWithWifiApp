package com.example.project1project

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project1project.data.MessageRepository
import kotlinx.coroutines.launch

//Connect Transport to viewModel
import com.example.project1project.communication.MessageTransport
import com.example.project1project.security.CryptoUtils

class PagerViewModel(
    private val repository: MessageRepository
) : ViewModel() {

    private var transport : MessageTransport? = null

    // UI-observed message list
    val messages = mutableStateListOf<PagerMessage>()

    init {
        loadMessages()
    }

    // Load messages from database
    private fun loadMessages() {
        viewModelScope.launch {
            messages.clear()
            messages.addAll(repository.getMessages())
        }
    }

    // Called when user presses SEND
    fun sendMessage(text: String) {
        if (text.isBlank()) return

        viewModelScope.launch {
            //Encrypted message
            val encrypted= CryptoUtils.encrypt(text)


            // save sent messages
            repository.send(text)
            loadMessages()

            //send encrypted messae throung transport
            transport?.send(encrypted)

        }
    }

    // Called when receiving encrypted message (network / simulation)
    fun receiveEncryptedMessage(encryptedText: String) {
        viewModelScope.launch {
            repository.receive(encryptedText)
            loadMessages()
        }
    }

    //For clearing the chart
    fun clearChat(){
        viewModelScope.launch{
            repository.clearAllMessages()
            messages.clear()  //Clear UI state immediately
        }
    }

    fun setTransport(t: MessageTransport){
        transport =t
        transport?.startListening { encrypted ->
            receiveEncryptedMessage(encrypted)
        }
    }
}

