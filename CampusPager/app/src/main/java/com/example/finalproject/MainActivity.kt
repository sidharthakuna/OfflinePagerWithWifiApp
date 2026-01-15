package com.example.finalproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.finalproject.ui.theme.FinalProjectTheme
import org.tensorflow.lite.schema.Padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.unit.Dp

//Entry point of the app
class MainActivity : ComponentActivity() {
    //viewModel instance (lifecycle aware)
    private val viewModel: PagerViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Allows content to draw behind system bars
        enableEdgeToEdge()
        //set Jetpack Compose UI
        setContent {
            //Apply app theme
            FinalProjectTheme {
                //Load main screen and pass viewModel
                PagerScreen(viewModel)
                }
            }
        }
    }

//Main UI screen of the app
@Composable
fun PagerScreen(viewModel:PagerViewModel){
    //Observe messages from viewModel
    val message=viewModel.messages
    //Used to control LazyColumn scrolling
    val listState=rememberLazyListState()
    //vertical layout for whole screen
    Column(
        modifier= Modifier
            .fillMaxSize()
            .background(Color.Black)
            .statusBarsPadding()
            .padding(16.dp)
    ){
        //App title
        Text(
            text="CAMPUS PAGER",
            color=Color(0xFF00C853),
            fontSize=22.sp,
            fontWeight=FontWeight.Bold,
            modifier=Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(12.dp))
        //Card that holds message List
        Card(
            modifier=Modifier
                .fillMaxWidth()
                .weight(1f), //Takes remaining space
            colors=CardDefaults.cardColors(
                containerColor=Color.DarkGray
            ),
            shape=RoundedCornerShape(12.dp)
        ){
            //Scrollable list of messages
            LazyColumn(
                state=listState,
                modifier= Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ){
                items(message) { msg ->
                    Text(
                        text = "• ${msg.text}",
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
        //Auto-scroll to bottom when new message arrives
        LaunchedEffect(message.size){
            if(message.isNotEmpty()){
                listState.animateScrollToItem(message.size-1)
            }
        }
        Spacer(modifier =Modifier.height(12.dp))

        //Input UI(Sends + fake Receive)
        PagerInputUI(
            onSendClick={viewModel.sendMessage(it)},
            onFakeReceive={viewModel.receiveMessage(it)}
        )
    }
}


