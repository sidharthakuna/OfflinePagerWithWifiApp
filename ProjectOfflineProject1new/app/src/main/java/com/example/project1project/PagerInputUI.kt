package com.example.project1project

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Composable function for message input section
@Composable
fun PagerInputUI(
    // Callback when SEND button is clicked
    onSendClick: (String) -> Unit,
    // Callback to simulate receiving a message
    onFakeReceive: (String) -> Unit
) {
    // Holds the text typed by the user
    var messageText by remember { mutableStateOf("") }

    // Helps keyboard focus
    val focusRequester = remember { FocusRequester() }

    // Vertical layout for input field and buttons
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {

        // Message input field
        OutlinedTextField(
            value = messageText,
            onValueChange = { messageText = it },
            placeholder = {
                Text("Type pager message...", color = Color.LightGray)
            },
            textStyle = LocalTextStyle.current.copy(
                color = Color.White,
                fontSize = 16.sp
            ),
            maxLines = 3,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color(0xFF00C853),
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color(0xFF00C853),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )

        // SEND button
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
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00C853)
            )
        ) {
            Text("SEND", color = Color.Black)
        }

        // FAKE RECEIVE button (for testing/demo)
        Button(
            onClick = { onFakeReceive("Hello from another phone") },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text("Fake Receive")
        }
    }

    // Automatically request focus so keyboard opens
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}
