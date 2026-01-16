package com.example.project1project.communication

/*
 * MessageTransport
 * -----------------
 * Abstract layer for sending & receiving messages
 * Can be implemented using:
 * - Wi-Fi
 * - Bluetooth
 * - NFC
 * - Simulation
 */

interface MessageTransport{
    //Send encrypted message to another device
    fun send(encryptedMessage:String)

    //start listening for incomming encrypted messages
    fun startListening(onMessageReceived:(String)-> Unit)

    //Stop listening (clean up)
    fun stop()
}