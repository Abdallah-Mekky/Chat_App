package com.task1.chat_app.ui.home

import com.task1.domain.model.Room


interface NavigatorHome {
    fun openNavigationView()
    fun navigateToAddRoomActivity()
    fun goToLoginActivity()
    fun openRoomDetailsActivity(room: Room)
    fun openChatActivity(room: Room)
}