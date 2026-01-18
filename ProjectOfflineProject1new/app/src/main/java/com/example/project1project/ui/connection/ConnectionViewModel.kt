package com.example.project1project.ui.connection

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.project1project.communication.MessageTransport
import com.example.project1project.communication.SimulatedTransport

class ConnectionViewModel : ViewModel() {

    var pagerId by mutableStateOf("")
        private set

    fun updatePagerId(id: String) {
        pagerId = id
    }
}

enum class TransportType {

    SIMULATED
}
