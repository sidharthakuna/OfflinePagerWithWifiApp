package com.example.project1project.communication

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

    private var listener: ((String) -> Unit)? = null

    override fun send(encryptedMessage: String) {
        // Simulate network delay
        GlobalScope.launch {
            delay(1000)
            listener?.invoke(encryptedMessage)
        }
    }

    override fun startListening(onMessageReceived: (String) -> Unit) {
        listener = onMessageReceived
    }

    override fun stop() {
        listener = null
    }
}
