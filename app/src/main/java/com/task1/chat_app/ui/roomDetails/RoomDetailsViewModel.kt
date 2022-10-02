package com.task1.chat_app.ui.roomDetails

import androidx.lifecycle.viewModelScope
import com.task1.chat_app.DataUtils
import com.task1.chat_app.base.BaseViewModel
import com.task1.domain.model.Room
import com.task1.domain.model.RoomUser
import com.task1.domain.repos.userRoomRepo.UserRoomRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomDetailsViewModel @Inject constructor(
    val userRoomRepo: UserRoomRepo,
    var currentRoom: Room
) : BaseViewModel<NavigatorRoomDetails>() {

    fun addUser() {

        progressDialogLiveData.value = true

        val user = RoomUser(DataUtils.currentUser?.userID, DataUtils.currentUser?.userName)


        viewModelScope.launch {


            try {

                userRoomRepo.addUserToRoom(user, currentRoom.roomId!!)
                progressDialogLiveData.value = false
                navigator?.navigateToChatActivity()

                try {

                    val sizeOfUsers =
                        userRoomRepo.getUsersOfRoomsFromFireStore(currentRoom.roomId!!)
                            .toObjects(RoomUser::class.java)

                    try {

                        userRoomRepo.updateNumberOfUsers(currentRoom.roomId!!, sizeOfUsers.size)

                    } catch (ex: Exception) {

                        progressDialogLiveData.value = false

                    }

                } catch (ex: Exception) {
                    progressDialogLiveData.value = false
                }

            } catch (ex: Exception) {
                progressDialogLiveData.value = false
            }

        }
    }
}