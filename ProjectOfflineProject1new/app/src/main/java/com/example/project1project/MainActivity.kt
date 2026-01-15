package com.example.project1project



// -------- ANDROID CORE IMPORTS --------
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
// -------- COMPOSE UI IMPORTS --------

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// -------- THEME IMPORT --------

import com.example.project1project.ui.theme.Project1projectTheme

/* =========================================================
   MAIN ACTIVITY
   ---------------------------------------------------------
   • Entry point of the Android app
   • Hosts Jetpack Compose UI
   • Connects ViewModel to UI
   ========================================================= */
class MainActivity : ComponentActivity() {

    // ViewModel instance
    // viewModels() ensures:
    // - ViewModel survives rotation
    // - UI state is preserved
    private val viewModel: PagerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enables edge-to-edge layout
        // Allows UI to draw behind status bar & navigation bar
        enableEdgeToEdge()

        // setContent starts Jetpack Compose
        setContent {

            // Apply app theme (colors, typography, shapes)
            Project1projectTheme {

                // Scaffold is a top-level layout:
                // - Handles safe areas
                // - Handles keyboard (IME)
                // - Provides padding automatically
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding() // Prevents keyboard overlap
                ) { paddingValues ->

                    // Main UI screen of the pager app
                    PagerScreen(
                        viewModel = viewModel,
                        modifier = Modifier
                            .padding(paddingValues) // Respect system padding
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

/* =========================================================
   PAGER SCREEN (MAIN UI)
   ---------------------------------------------------------
   • Shows app title
   • Displays message list
   • Shows input field + send button
   ========================================================= */
@Composable
fun PagerScreen(
    viewModel: PagerViewModel,
    modifier: Modifier = Modifier
) {
    // messages is a State list from ViewModel
    // Any change automatically updates the UI
    val messages = viewModel.messages

    // LazyColumn scroll controller
    // Used to auto-scroll when a new message arrives
    val listState = rememberLazyListState()

    // Root vertical layout for the entire screen
    Column(
        modifier = modifier
            .fillMaxSize()           // Take full screen
            .background(Color.Black) // Dark background
            .padding(16.dp)          // Outer padding
    ) {

        /* ---------------- APP TITLE ---------------- */
        Text(
            text = "CAMPUS PAGER",
            color = Color(0xFF00C853),  // Green color
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(12.dp))

        /* ---------------- MESSAGE LIST CONTAINER ---------------- */
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), // Takes all remaining vertical space
            colors = CardDefaults.cardColors(
                containerColor = Color.DarkGray
            ),
            shape = RoundedCornerShape(12.dp)
        ) {

            // IMPORTANT:
            // Only ONE LazyColumn should exist
            // LazyColumn efficiently displays scrolling lists
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {

                // Loop through each message
                items(messages) { msg ->

                    // Row is used to align messages
                    // SENT  -> right side
                    // RECEIVED -> left side
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement =
                            if (msg.type == MessageType.SENT)
                                Arrangement.End    // Sent messages on right
                            else
                                Arrangement.Start  // Received messages on left
                    ) {

                        // Message bubble UI
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor =
                                    if (msg.type == MessageType.SENT)
                                        Color(0xFF00C853) // Green bubble (sent)
                                    else
                                        Color.Gray        // Gray bubble (received)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {

                            // Message text
                            Text(
                                text = msg.text,
                                modifier = Modifier.padding(8.dp),
                                color = Color.Black,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }

        /* ---------------- AUTO-SCROLL LOGIC ---------------- */
        // Whenever message count changes,
        // scroll to the latest message automatically
        LaunchedEffect(messages.size) {
            if (messages.isNotEmpty()) {
                listState.animateScrollToItem(messages.size - 1)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        /* ---------------- INPUT SECTION ---------------- */
        // Bottom input UI for sending messages
        PagerInputUI(
            onSendClick = { text ->
                // Delegate send action to ViewModel
                viewModel.sendMessage(text)
            }
        )
    }
}
