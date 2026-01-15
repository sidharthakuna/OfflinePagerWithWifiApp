package com.example.practiceproject

import android.os.Bundle
import androidx.compose.material3.OutlinedTextField
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Button
import androidx.compose.ui.unit.sp
import com.example.practiceproject.ui.theme.PracticeProjectTheme
import androidx.compose.ui.Alignment
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import android.widget.Button
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticeProjectTheme {
                var name by remember{
                    mutableStateOf("")
            }
                var names by remember{
                    mutableStateOf(listOf<String>())
                }
                Column(
                    modifier=Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ){
                    Row(
                        modifier=Modifier.fillMaxWidth()
                    ){
                        OutlinedTextField(
                            value=name,
                            onValueChange={ text->
                                name=text

                            }
                        )
                        Button(onClick={
                            if(name.isNotBlank()){
                                names=names+name
                            }


                        }){
                            Text(text="Add")
                        }
                    }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        items(names) { currentName ->
                            Text(
                                text = currentName,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }


                }
            }
        }
    }
}

