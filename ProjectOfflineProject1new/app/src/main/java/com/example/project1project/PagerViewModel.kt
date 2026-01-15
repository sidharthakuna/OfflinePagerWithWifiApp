package com.example.project1project


import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.project1project.data.MessageRepository
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

        //sync UI state
        messages.clear()
        messages.addAll(repository.getMessages())
    }

    // Simulates receiving message from another phone
    fun receiveMessage(text: String) {
        repository.receive(text)
        messages.clear()
        messages.addAll(repository.getMessages())
    }
}
