package com.example.project1project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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

// Entry point of the app
class MainActivity : ComponentActivity() {

    // ViewModel instance
    private val viewModel: PagerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enables edge-to-edge UI (status bar / navigation bar)
        enableEdgeToEdge()

        setContent {
            Project1projectTheme {

                // Scaffold provides proper layout handling
                // imePadding() is REQUIRED for keyboard
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding()
                ) { paddingValues ->

                    // Main Screen
                    PagerScreen(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize(),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

// Main UI screen of the app
@Composable
fun PagerScreen(
    viewModel: PagerViewModel,
    modifier: Modifier = Modifier
) {
    // Observe messages from ViewModel
    val messages = viewModel.messages

    // Used to control LazyColumn scrolling
    val listState = rememberLazyListState()

    // Vertical layout for whole screen
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {

        // App title
        Text(
            text = "CAMPUS PAGER",
            color = Color(0xFF00C853),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Card that holds message list
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), // Takes remaining space
            colors = CardDefaults.cardColors(
                containerColor = Color.DarkGray
            ),
            shape = RoundedCornerShape(12.dp)
        ) {

            // Scrollable list of messages
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                items(messages) { msg ->
                    Text(
                        text = "• ${msg.text}",
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }

        // Auto-scroll to bottom when new message arrives
        LaunchedEffect(messages.size) {
            if (messages.isNotEmpty()) {
                listState.animateScrollToItem(messages.size - 1)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Input UI (Send + Fake Receive)
        PagerInputUI(
            onSendClick = { viewModel.sendMessage(it) },
            onFakeReceive = { viewModel.receiveMessage(it) }
        )
    }
}
