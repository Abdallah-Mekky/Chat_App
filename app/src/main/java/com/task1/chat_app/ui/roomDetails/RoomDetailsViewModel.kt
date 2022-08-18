package com.task1.chat_app.ui.roomDetails

import com.task1.chat_app.DataUtils
import com.task1.chat_app.base.BaseViewModel
import com.task1.chat_app.database.addUserToRoom
import com.task1.chat_app.database.getUsersOfRoomsFromFireStore
import com.task1.chat_app.database.model.Room
import com.task1.chat_app.database.model.RoomUser
import com.task1.chat_app.database.updateNumberOfUsers

class RoomDetailsViewModel : BaseViewModel<NavigatorRoomDetails>() {

    var currentRoom: Room? = null

    fun addUser() {

        progressDialogLiveData.value = true

        var user = RoomUser(DataUtils.currentUser?.userID, DataUtils.currentUser?.userName)

        addUserToRoom(user, currentRoom?.roomId!!, onSuccessListener = {

            progressDialogLiveData.value = false
            navigator?.navigateToChatActivity()


            getUsersOfRoomsFromFireStore(currentRoom?.roomId!!, onSuccessListener = {

                var sizeOfUsers = it.toObjects(RoomUser::class.java)

                updateNumberOfUsers(currentRoom?.roomId!!, sizeOfUsers.size, onSuccessListener = {

                },
                    onFailureListener = {
                        progressDialogLiveData.value = false
                    })

            }, onFailureListener = {
                progressDialogLiveData.value = false
            })

        }, onFailureListener = {

            progressDialogLiveData.value = false
        })
    }
}