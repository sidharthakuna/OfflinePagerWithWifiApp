package com.example.project1project.ui.connection

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.project1project.communication.MessageTransport



@Composable
fun ConnectionScreen(
    viewModel : ConnectionViewModel,
    onConnected : (MessageTransport)-> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement =Arrangement.spacedBy(16.dp)
    ){
        Text(
            text = "Campus Pager",
            fontSize=24.sp,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = viewModel.pagerId,
            onValueChange = viewModel::updatePagerId,
            label = { Text("Pager Id")},
            modifier = Modifier.fillMaxWidth()
        )

        Text("Transport")

        Row {
            RadioButton(
                selected = viewModel.selectedTransport == TransportType.SIMULATED,
                onClick = { viewModel.selectTransport(TransportType.SIMULATED) }
            )
            Text("Simulated")

            Spacer(modifier = Modifier.width(16.dp))

            RadioButton(
                selected = viewModel.selectedTransport == TransportType.WIFI,
                onClick = { viewModel.selectTransport(TransportType.WIFI) }
            )

            Text("Wi-Fi")

            Button(
                onClick = {
                    viewModel.connect(onConnected)
                },
                modifier = Modifier.fillMaxWidth()
            ){
                Text("CONNECT")
            }


        }
    }
}