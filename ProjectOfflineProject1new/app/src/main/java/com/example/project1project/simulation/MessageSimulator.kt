package com.example.project1project.simulation

import com.example.project1project.security.CryptoUtils
import kotlinx.coroutines.delay
import kotlin.random.Random


/*
 * MessageSimulator
 * ----------------
 * Simulates incoming encrypted messages
 * like they are coming from a server or another user
 */
object MessageSimulator{
    private val replies =listOf(
        "Message received 👍",
        "Okay!",
        "See you soon",
        "Noted",
        "Sounds good",
        "Thanks!"
    )
    //Return encrypted reply after delay
    suspend fun simulateIncomingMessage():String{
        delay(2000)    //simylate network delay (2seconds)

        val randomReply =replies.random()
        return CryptoUtils.encrypt(randomReply)
    }
}