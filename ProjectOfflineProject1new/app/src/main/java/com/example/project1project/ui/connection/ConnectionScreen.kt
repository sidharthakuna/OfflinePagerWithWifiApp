package com.example.project1project.ui.connection

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project1project.communication.MessageTransport
import com.example.project1project.communication.SimulatedTransport

@Composable
fun ConnectionScreen(
    viewModel: ConnectionViewModel,
    onConnected: (MessageTransport) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // App title
        Text(
            text = "Campus Pager",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        // Pager ID input
        OutlinedTextField(
            value = viewModel.pagerId,
            onValueChange = viewModel::updatePagerId,
            label = { Text("Pager ID") },
            modifier = Modifier.fillMaxWidth()
        )

        Divider()

        // Transport info (only simulated now)
        Text(
            text = "Transport: Simulated",
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Connect button
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                // Always connect using SimulatedTransport
                onConnected(SimulatedTransport())
            }
        ) {
            Text("CONNECT")
        }
    }
}
