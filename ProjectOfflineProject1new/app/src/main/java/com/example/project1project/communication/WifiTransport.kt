package com.example.project1project.communication

import android.content.Context
import android.util.Log


/**
 * WifiTransport (STUB)
 * --------------------
 * Placeholder for future Wi-Fi / mesh implementation.
 * Currently disabled during cleanup phase.
 */

class WifiTransport(
    private val context: Context
) : MessageTransport{

    override fun send(encryptedMessage:String){
        Log.w("WifiTransport","send() called but Wi-fi trasport is disabled")
    }

    override fun startListening(onMessageReceived: (String) -> Unit) {
        Log.w("WifiTransport","startListening() called but wifi is transport is disabled")
    }

    override fun stop(){
        Log.w("WifiTransport","stop() call but Wifi transport is disabled")
    }
}