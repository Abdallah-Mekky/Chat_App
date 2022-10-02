package com.task1.domain.model

import java.text.SimpleDateFormat
import java.util.*

data class Message(
    var id: String? = null,
    var messageContent: String? = null,
    var messageDateTime: Long? = null,
    var senderId: String? = null,
    var senderName: String? = null
) {


    fun formatDate(): String {

        val date = Date(messageDateTime!!)

        val simpleDateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

        return simpleDateFormat.format(date)

    }


}