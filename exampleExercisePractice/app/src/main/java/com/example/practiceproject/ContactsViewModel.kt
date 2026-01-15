package com.example.practiceproject
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White


class ContactsViewModel {
    var backgroundColor by mutableStateOf(White)
        private set
    fun changeBackgroundColor(){
        backgroundColor=Color.Red

    }
}