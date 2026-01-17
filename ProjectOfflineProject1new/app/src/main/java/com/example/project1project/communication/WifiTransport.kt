package com.example.project1project.communication

import android.content.Context
import android.util.Log
import com.example.project1project.mesh.MessagePacket

/**
 * WifiTransport (STUB)
 * --------------------
 * Placeholder for future Wi-Fi / mesh implementation.
 * Disabled intentionally during cleanup & mesh design phase.
 */
class WifiTransport(
    private val context: Context
) : MessageTransport {

    override fun send(packet: MessagePacket) {
        Log.w(
            "WifiTransport",
            "send(packetId=${packet.packetId}) called but Wi-Fi transport is disabled"
        )
    }

    override fun startListening(onPacketReceived: (MessagePacket) -> Unit) {
        Log.w(
            "WifiTransport",
            "startListening() called but Wi-Fi transport is disabled"
        )
    }

    override fun stop() {
        Log.w("WifiTransport", "stop() called but Wi-Fi transport is disabled")
    }
}
