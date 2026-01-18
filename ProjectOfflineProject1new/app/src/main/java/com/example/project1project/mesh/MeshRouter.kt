package com.example.project1project.mesh


/**
 * MeshRouter
 * ----------
 * This class contains ALL mesh logic:
 * - deduplication
 * - TTL / hop count
 * - delivery decision
 * - forwarding
 *
 * IMPORTANT:
 * - It does NOT know about Wi-Fi or Bluetooth
 * - It does NOT know about UI or DB
 * - It only processes packets
 */

class MeshRouter(
    private val myPagerId :String,
    private val forward:(MessagePacket) -> Unit,
    private val deliver:(MessagePacket)-> Unit
){

    //prevet infinite loops
    private val seenPacketIds= mutableSetOf<String>()

    fun onPacketReceived(packet: MessagePacket) {

        // 1️⃣ Drop duplicate packets
        if (!seenPacketIds.add(packet.packetId)) return

        // 2️⃣ Drop packet if TTL exceeded
        if (packet.hopCount >= packet.maxHops) return

        // 3️⃣ If this packet is for ME → deliver
        if (packet.toPagerId == myPagerId) {
            deliver(packet)
            return
        }

        // 4️⃣ Otherwise act as BRIDGE → forward blindly
        // Bridge does NOT read payload
        forward(
            packet.copy(hopCount = packet.hopCount + 1)
        )
    }
}