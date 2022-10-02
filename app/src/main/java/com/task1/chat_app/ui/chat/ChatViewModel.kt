package com.task1.chat_app.ui.chat

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.task1.chat_app.DataUtils
import com.task1.chat_app.base.BaseViewModel
import com.task1.domain.model.Message
import com.task1.domain.model.Room
import com.task1.domain.model.RoomUser
import com.task1.domain.repos.messageRepo.MessageRepo
import com.task1.domain.repos.userRoomRepo.UserRoomRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(val userRoomRepo: UserRoomRepo,
                                        val messageRepo: MessageRepo,
                                        var currentRoom: Room,
                                        var time: Long) : BaseViewModel<NavigatorChat>() {


    var messageContent = ObservableField<String>()
    var newMessagesListMutableLiveData = MutableLiveData<MutableList<Message>>()
    var newMessagesList: LiveData<MutableList<Message>> = newMessagesListMutableLiveData


    fun send() {

        if (valdiate()) {

            sendMessage()
        }
    }

    fun sendMessage() {
        val message = Message(
            messageContent = messageContent.get(),
            messageDateTime = time,
            senderId = DataUtils.currentUser?.userID,
            senderName = DataUtils.currentUser?.userName
        )

        viewModelScope.launch {

            try {

                messageContent.set(null)
                messageRepo.addMessageToFirestore(message, currentRoom.roomId!!)

            } catch (ex: Exception) {

                messageLiveData.value = ex.localizedMessage
            }
        }
    }

    private fun valdiate(): Boolean {

        var isVaild = true

        if (messageContent.get().isNullOrBlank()) {

            toastMessageLiveData.value = "Empty Message"

            isVaild = false
        }

        return isVaild
    }


    fun deleteUser() {


        viewModelScope.launch {

            try {

                userRoomRepo.deleteUserFromRoomUser(
                    DataUtils.currentUser?.userID,
                    currentRoom.roomId!!
                )
                messageLeaveRoomLiveData.value = "User Successfully Deleted From This Room"

                try {

                    val sizeOfUsers =
                        userRoomRepo.getUsersOfRoomsFromFireStore(currentRoom.roomId!!)
                            .toObjects(RoomUser::class.java)

                    try {

                        userRoomRepo.updateNumberOfUsers(currentRoom.roomId!!, sizeOfUsers.size)

                    } catch (ex: Exception) {

                        toastMessageLiveData.value = ex.localizedMessage
                    }

                } catch (ex: Exception) {
                    toastMessageLiveData.value = ex.localizedMessage

                }
            } catch (ex: Exception) {

                toastMessageLiveData.value = ex.localizedMessage

            }
        }
    }


    fun getMessages() {

        viewModelScope.launch {

            try {

                val result = messageRepo.getMessageCollectionRef(currentRoom.roomId!!)

                try {

                    messageRepo.getMessages(
                        result,
                        newMessagesListMutableLiveData,
                        toastMessageLiveData
                    )
                } catch (ex: Exception) {

                    toastMessageLiveData.value = "error in get messages"
                }

            } catch (ex: Exception) {

                toastMessageLiveData.value = "error in get messages"
            }
        }
    }

}

