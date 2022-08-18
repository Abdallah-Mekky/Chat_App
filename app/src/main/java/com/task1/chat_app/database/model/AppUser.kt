package com.task1.chat_app.database.model

data class AppUser(
    var userID: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var userName: String? = null,
    var email: String? = null
)