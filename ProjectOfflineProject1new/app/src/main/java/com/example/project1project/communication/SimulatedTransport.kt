package com.example.project1project.communication
import com.example.project1project.mesh.MessagePacket
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch



/*
 * SimulatedTransport
 * ------------------
 * Fake transport used for MVP testing
 * Replaced later by Wi-Fi / Bluetooth
 */
class SimulatedTransport : MessageTransport {

    private var listener: ((MessagePacket) -> Unit)? = null

    @OptIn(DelicateCoroutinesApi::class)
    override fun send(packet: MessagePacket) {
        // Simulate network delay
        GlobalScope.launch {
            delay(500)
            listener?.invoke(packet)
        }
    }

    override fun startListening(onPacketReceived: (MessagePacket) -> Unit) {
        listener = onPacketReceived
    }

    override fun stop() {
        listener = null
    }
}
