package com.example.project1project.communication

import com.example.project1project.mesh.MessagePacket

class BluetoothMeshTransport : MessageTransport {

    override fun startListening(onPacketReceived: (MessagePacket) -> Unit) {
        // Bluetooth discovery + sockets
    }

    override fun send(packet: MessagePacket) {
        // Send to all connected Bluetooth peers
    }

    override fun stop() {
        // Cleanup
    }
}
