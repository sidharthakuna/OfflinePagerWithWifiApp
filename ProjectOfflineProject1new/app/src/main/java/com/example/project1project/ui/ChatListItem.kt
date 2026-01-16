package com.example.project1project.ui

import com.example.project1project.PagerMessage


sealed class ChatListItem {
    data class DateHeader(
        val title:String
    ):ChatListItem()
    data class MessageItems(
        val message: PagerMessage
    ): ChatListItem()
}
