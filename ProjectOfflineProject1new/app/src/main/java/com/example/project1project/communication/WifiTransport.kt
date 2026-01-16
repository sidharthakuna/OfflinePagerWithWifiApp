package com.example.project1project.communication

import android.content.Context
import android.net.wifi.p2p.*
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.*
import java.net.ServerSocket
import java.net.Socket

class WifiTransport(
    private val context: Context
):MessageTransport{
    private val manager =
        context.getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager

    private val channel=
        manager.initialize(context,context.mainLooper,null)

    private var receiver:((String)->Unit)? = null

    override fun startListening(onMessageReceived:(String)->Unit){
        receiver = onMessageReceived
    }

    override fun send(encryptedMessage:String){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val socket=Socket("192.168.49.1", 8888)
                val output=PrintWriter(socket.getOutputStream(),true)
                output.println(encryptedMessage)
                socket.close()
            } catch(e:Exception){
                Log.e("WifiTransport","Send failed",e)
            }
        }
    }
    override fun stop(){
        receiver=null
    }
    /* Server side */
    fun startServer(){
        CoroutineScope(Dispatchers.IO).launch{
            try{
                val serverSocket =ServerSocket(8888)
                val client = serverSocket.accept()
                val input = BufferedReader(InputStreamReader(client.getInputStream()))
                val message = input.readLine()

                receiver?.invoke(message)
                client.close()
                serverSocket.close()
            } catch(e: Exception){
                Log.e("WifiTransport","Server error",e)
            }

        }
    }

    fun discoverPeers(){
        manager.discoverPeers(channel,object:WifiP2pManager.ActionListener{
            override fun onSuccess(){
                Log.d("WifiTransport","Peer discovery started")
            }
            override fun onFailure(reason:Int){
                Log.d("WifiTransport","Discovery failed : $reason")
            }
        })

    }

}