package com.example.project1project

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//Composable function for message input section
@Composable
fun PagerInputUI(
    //Call back When SEND  button is clicked
    onSendClick:(String )-> Unit,
    //Callback to simulate receiving a message
    onFakeReceive:(String)-> Unit
){
    //Holds the text typed by the user
    var messageText by remember { mutableStateOf("")}
    //Vertical layout for input field and buttons
    Column(verticalArrangement =Arrangement.spacedBy(12.dp)){
        //Message input filed
        OutlinedTextField(
            value=messageText,
            onValueChange ={messageText=it},
            //text color for visibility

            placeholder= {
                Text(
                    "Type pager message...",
                    color=Color.LightGray
                    )
            },
            textStyle=LocalTextStyle.current.copy(
                color=Color.White,
                fontSize=16.sp
            ),
            maxLines=3,
            modifier=Modifier.fillMaxWidth(),

            //Color for dark background
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                disabledTextColor = Color.White,

                focusedBorderColor = Color(0xFF00C853),
                unfocusedBorderColor = Color.Gray,

                cursorColor = Color(0xFF00C853),

                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )
        //SEND  button
        Button(
            onClick={
                if(messageText.isNotBlank()){
                    onSendClick(messageText)
                    messageText=""
                }
            },
            modifier=Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors=ButtonDefaults.buttonColors(
                containerColor=Color(0xFF00C853)
            )
        ){
            Text("SEND",color=Color.Black)
        }
        //FAKE RECIEVE button(for testing/demo)
        Button(
            onClick={ onFakeReceive("Hello from another phone")},
            modifier= Modifier
                .fillMaxWidth()
                .height(52.dp)
        ){
            Text("Fake Receive")
        }

    }

}
