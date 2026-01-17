package com.example.project1project

import com.example.project1project.mesh.MessagePacket
import com.example.project1project.identity.PagerIdManager
import java.util.UUID

import android.content.Context

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project1project.data.MessageRepository
import kotlinx.coroutines.launch

//Connect Transport to viewModel
import com.example.project1project.communication.MessageTransport
import com.example.project1project.security.CryptoUtils

class PagerViewModel(
    private val repository: MessageRepository,
    private val context:Context
) : ViewModel() {

    val myPagerId:String = PagerIdManager.getPagerId(context)
        ?: throw IllegalStateException("pager ID not set")

    private var transport : MessageTransport? = null

    //Mesh loop-prevention memory
    private val seenPacketIds=mutableSetOf<String>()

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
    fun sendMessage(text:String,toPagerId:String){
        if(text.isBlank() || toPagerId.isBlank()) return

        viewModelScope.launch {
            val encrypted= CryptoUtils.encrypt(text)

            val packet= MessagePacket(
                packetId = UUID.randomUUID().toString(),
                fromPagerId = myPagerId,
                toPagerId = toPagerId,
                encryptedPayload = encrypted,
                hopCount = 0,
                maxHops = 6
            )

            //Save only sender's copy
            repository.send(text)
            loadMessages()

            //sends packet into the mesh
            transport?.send(packet)
        }
    }
    private fun handleIncomingPacket(packet: MessagePacket) {
        if (seenPacketIds.contains(packet.packetId)) return
        seenPacketIds.add(packet.packetId)

        if (packet.hopCount >= packet.maxHops) return

        if (packet.toPagerId == myPagerId) {
            val payload = packet.encryptedPayload

            viewModelScope.launch {
                repository.receive(payload)
                loadMessages()
            }
            return
        }

        transport?.send(
            packet.copy(hopCount = packet.hopCount + 1)
        )
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
        transport?.startListening { packet ->
            //step 3 will implement this
            handleIncomingPacket(packet)
        }
    }
}

