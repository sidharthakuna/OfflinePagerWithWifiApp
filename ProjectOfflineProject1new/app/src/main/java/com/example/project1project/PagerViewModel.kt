package com.example.project1project


import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project1project.communication.MessageTransport
import com.example.project1project.data.MessageRepository
import com.example.project1project.mesh.MeshRouter
import com.example.project1project.mesh.MessagePacket
import com.example.project1project.security.CryptoUtils
import kotlinx.coroutines.launch
import java.util.UUID



class PagerViewModel(
    private val repository: MessageRepository,
    val myPagerId:String
) : ViewModel() {

    private lateinit var meshRouter: MeshRouter

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
            repository.send(
                text=text,
                myPagerId=myPagerId,
                receiverPagerId=toPagerId
            )
            loadMessages()

            //sends packet into the mesh
            transport?.send(packet)
        }
    }
    private fun handleIncomingPacket(packet: MessagePacket) {

        if (!seenPacketIds.add(packet.packetId)) return

        if (packet.toPagerId != myPagerId) return   // ❗ TEMP FIX

        viewModelScope.launch {
            repository.receive(
                encryptedText = packet.encryptedPayload,
                senderPagerId = packet.fromPagerId
            )
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

    fun setTransport(t: MessageTransport) {
        transport?.stop()
        transport = t

        meshRouter = MeshRouter(
            myPagerId = myPagerId,

            // Forwarding = send to transport
            forward = { packet ->
                transport?.send(packet)
            },

            // Delivery = store in DB
            deliver = { packet ->
                viewModelScope.launch {
                    repository.receive(
                        encryptedText = packet.encryptedPayload,
                        senderPagerId = packet.fromPagerId
                    )
                    loadMessages()
                }
            }
        )

        transport?.startListening { packet ->
            meshRouter.onPacketReceived(packet)
        }
    }


}

