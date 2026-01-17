package com.example.project1project

import com.example.project1project.mesh.MessagePacket
import com.example.project1project.identity.PagerIdManager
import java.util.UUID

import android.content.Context

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project1project.data.MessageRepository
import kotlinx.coroutines.launch

//Connect Transport to viewModel
import com.example.project1project.communication.MessageTransport
import com.example.project1project.security.CryptoUtils

class PagerViewModel(
    private val repository: MessageRepository,
    private val context:Context
) : ViewModel() {

    lateinit var myPagerId : String

    private var transport : MessageTransport? = null

    //Mesh loop-prevention memory
    private val seenPacketIds=mutableSetOf<String>()

    // UI-observed message list
    val messages = mutableStateListOf<PagerMessage>()

    init {
        myPagerId= PagerIdManager.getOrCreatePagerId(context)
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
    private fun handleIncomingPacket(packet:MessagePacket){
        //Drop if already seen
        if(seenPacketIds.contains(packet.packetId)) return
        seenPacketIds.add(packet.packetId)

        //Drop if hop limit exceeded
        if(packet.hopCount >= packet.maxHops) return

        //if this device is the receiver -> decrypt & store
        if(packet.toPagerId==myPagerId){
            viewModelScope.launch {
                repository.receive(packet.encryptedPayload)
                loadMessages()
            }
            return
        }

        //Otherwise -> forward(hop)
        val forwardedPacket = packet.copy(
            hopCount= packet.hopCount+1
        )

        transport?.send(forwardedPacket)
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

