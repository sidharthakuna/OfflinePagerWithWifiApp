package com.example.finalproject

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import java.util.UUID

class PagerViewModel: ViewModel(){
    //HOlding all messages
    val  messages=mutableStateListOf<PagerMessage>()

    //called when user sends a message
    fun sendMessage(text:String){
        val message=PagerMessage(
            id =UUID.randomUUID().toString(),
            text =text,
            timestamp =System.currentTimeMillis()
        )
        messages.add(message)
    }

    //Simulates receiving message from another phone
    fun receiveMessage(text:String ){
        val message=PagerMessage(
            id=UUID.randomUUID().toString(),
            text="Friend : $text",
            timestamp=System.currentTimeMillis()
        )
    }
}