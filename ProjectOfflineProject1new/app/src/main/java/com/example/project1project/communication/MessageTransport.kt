package com.example.project1project.communication

import com.example.project1project.mesh.MessagePacket

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
    fun send(packet:MessagePacket)

    //start listening for incoming encrypted messages
    fun startListening(onPacketReceived:(MessagePacket)-> Unit)

    //Stop listening (clean up)
    fun stop()
}