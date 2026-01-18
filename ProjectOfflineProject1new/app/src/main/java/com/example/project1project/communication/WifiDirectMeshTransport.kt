package com.example.project1project.communication

import android.content.Context
import android.net.wifi.p2p.WifiP2pDevice
import com.example.project1project.mesh.MessagePacket

/**
 * WifiDirectMeshTransport
 * -----------------------
 * RESPONSIBILITY:
 * - Discover nearby devices using Wi-Fi Direct
 * - Maintain a list of connected peers
 * - Send MessagePacket objects to peers
 * - Receive MessagePacket objects from peers
 *
 * DOES NOT:
 * ❌ decrypt messages
 * ❌ inspect payload
 * ❌ apply mesh routing logic
 *
 * Mesh routing is handled by MeshRouter
 */
class WifiDirectMeshTransport(
    private val context: Context
) : MessageTransport {

    /**
     * List of peers currently connected via Wi-Fi Direct.
     * Each peer represents a nearby phone.
     *
     * In PHASE 2:
     * - This will usually contain only ONE peer
     *
     * In PHASE 3:
     * - This can contain MANY peers (mesh)
     */
    private val connectedPeers = mutableSetOf<WifiP2pDevice>()

    /**
     * Called by ViewModel to start listening for packets
     */
    override fun startListening(onPacketReceived: (MessagePacket) -> Unit) {
        // TODO PHASE 2:
        // 1. Initialize WifiP2pManager
        // 2. Register BroadcastReceiver
        // 3. Discover peers
        // 4. Establish socket connection
        // 5. Read MessagePacket from socket
        // 6. Call onPacketReceived(packet)
    }

    /**
     * Sends a packet to ALL connected peers.
     *
     * This method enables FLOODING when multiple peers exist.
     */
    override fun send(packet: MessagePacket) {
        for (peer in connectedPeers) {
            sendToPeer(peer, packet)
        }
    }

    /**
     * Sends a MessagePacket to ONE peer.
     *
     * This is a LOW-LEVEL operation.
     * Actual socket code will live here.
     */
    private fun sendToPeer(
        peer: WifiP2pDevice,
        packet: MessagePacket
    ) {
        // TODO PHASE 2:
        // 1. Open socket to peer (peer.deviceAddress)
        // 2. Write ObjectOutputStream(packet)
        // 3. Close socket
    }

    override fun stop() {
        // TODO:
        // Close sockets
        // Unregister receivers
        connectedPeers.clear()
    }
}
