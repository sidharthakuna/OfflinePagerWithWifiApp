package com.example.project1project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project1project.ui.theme.Project1projectTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Project1projectTheme {
                PagerScreen()
            }
        }
    }
}

@Composable
fun PagerScreen() {
    // ✅ Local message storage (offline)
    val messages = remember { mutableStateListOf<String>() }
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        // 📟 Header
        Text(
            text = "CAMPUS PAGER",
            color = Color(0xFF00C853),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 📜 Message history
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
            shape = RoundedCornerShape(12.dp)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                items(messages) { msg ->
                    Text(
                        text = "• $msg",
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }

        // Auto-scroll to latest message
        LaunchedEffect(messages.size) {
            if (messages.isNotEmpty()) {
                listState.animateScrollToItem(messages.size - 1)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ✉️ Input + Send button
        PagerInputUI { message ->
            messages.add(message)
        }
    }
}

@Composable
fun PagerInputUI(
    onSendClick: (String) -> Unit
) {
    var messageText by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = messageText,
            onValueChange = { messageText = it },
            placeholder = { Text("Type pager message...") },
            maxLines = 3,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                if (messageText.isNotBlank()) {
                    onSendClick(messageText)
                    messageText = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C853)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "SEND",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}


