package com.example.project1project

// -------- SECURITY IMPORT --------
import com.example.project1project.security.CryptoUtils

// -------- ANDROID CORE IMPORTS --------
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels

// -------- COMPOSE UI IMPORTS --------
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.project1project.ui.theme.Project1projectTheme

/* =========================================================
   MAIN ACTIVITY
   ---------------------------------------------------------
   • Entry point of the Android app
   • Hosts Jetpack Compose UI
   • Connects ViewModel to UI
   ========================================================= */
class MainActivity : ComponentActivity() {

    // ViewModel survives rotation & configuration changes
    private val viewModel: PagerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            Project1projectTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                ) { paddingValues ->
                    PagerScreen(
                        viewModel = viewModel,
                        modifier = Modifier
                            .padding(paddingValues)
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
   • Shows title
   • Displays encrypted messages (decrypted for display)
   • Input field + send button
   ========================================================= */
@Composable
fun PagerScreen(
    viewModel: PagerViewModel,
    modifier: Modifier = Modifier
) {
    val messages = viewModel.messages
    val listState = rememberLazyListState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {

        // -------- APP TITLE --------
        Text(
            text = "CAMPUS PAGER",
            color = Color(0xFF00C853),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // -------- MESSAGE LIST --------
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = CardDefaults.cardColors(
                containerColor = Color.DarkGray
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                items(
                    items = messages,
                    key = { it.id }
                ) { msg ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement =
                            if (msg.type == MessageType.SENT)
                                Arrangement.End
                            else
                                Arrangement.Start
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor =
                                    if (msg.type == MessageType.SENT)
                                        Color(0xFF00C853)
                                    else
                                        Color.Gray
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            // 🔐 Decrypted ONLY for display (E2EE safe)
                            Text(
                                text = viewModel.getDisplayText(msg.text),
                                modifier = Modifier.padding(8.dp),
                                color = Color.Black,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }

        // -------- AUTO SCROLL --------
        LaunchedEffect(messages.size) {
            if (messages.isNotEmpty()) {
                listState.animateScrollToItem(messages.size - 1)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // -------- INPUT SECTION --------
        PagerInputUI(
            onSendClick = { text ->
                viewModel.sendMessage(text)
            }
        )
    }
}
