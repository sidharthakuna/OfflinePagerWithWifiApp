package com.example.project1project.mesh

import java.io.Serializable

data class MessagePacket (
    val packetId:String,
    val fromPagerId:String,
    val toPagerId: String,
    val encryptedPayload:String,
    val hopCount:Int,
    val maxHops:Int,

) : Serializable