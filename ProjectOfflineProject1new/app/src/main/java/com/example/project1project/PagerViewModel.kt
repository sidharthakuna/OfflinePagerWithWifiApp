package com.example.project1project

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project1project.data.MessageRepository
import com.example.project1project.simulation.MessageSimulator
import kotlinx.coroutines.launch

class PagerViewModel(
    private val repository: MessageRepository
) : ViewModel() {

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
            // save sent messages
            repository.send(text)
            loadMessages()

            //simulate incoming encrypted message
            val encryptedReply=
                MessageSimulator.simulateIncomingMessage()
            repository.receive(encryptedReply)
            loadMessages()
        }
    }

    // Called when receiving encrypted message (network / simulation)
    fun receiveEncryptedMessage(encryptedText: String) {
        viewModelScope.launch {
            repository.receive(encryptedText)
            loadMessages()
        }
    }
}
