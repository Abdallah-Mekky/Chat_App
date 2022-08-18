package com.task1.chat_app.ui.chat

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.Query
import com.task1.chat_app.DataUtils
import com.task1.chat_app.base.BaseViewModel
import com.task1.chat_app.database.*
import com.task1.chat_app.database.model.Message
import com.task1.chat_app.database.model.Room
import com.task1.chat_app.database.model.RoomUser
import java.util.*

class ChatViewModel : BaseViewModel<NavigatorChat>() {

    var messageContent = ObservableField<String>()
    var currentRoom: Room? = null
    var newMessagesList = MutableLiveData<MutableList<Message>>()
    var time = Date().time


    fun send() {

        if (valdiate()) {

            sendMessage()
        }
    }

    fun sendMessage() {
        var message = Message(
            messageContent = messageContent.get(),
            messageDateTime = time,
            senderId = DataUtils.currentUser?.userID,
            senderName = DataUtils.currentUser?.userName
        )

        addMessageToFirestore(message, currentRoom?.roomId!!, onSuccessListener = {

            messageContent.set(null)
        }, onFailureListener = {

            messageLiveData.value = it.localizedMessage
        })

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

        deleteUserFromRoomUser(
            DataUtils.currentUser?.userID,
            currentRoom?.roomId!!,
            onSuccessListener = {

                messageLeaveRoomLiveData.value = "User Successfully Deleted From This Room"

                getUsersOfRoomsFromFireStore(currentRoom?.roomId!!, onSuccessListener = {

                    var sizeOfUsers = it.toObjects(RoomUser::class.java)

                    updateNumberOfUsers(currentRoom?.roomId!!,
                        sizeOfUsers.size,
                        onSuccessListener = {

                        },
                        onFailureListener = {
                            toastMessageLiveData.value = it.localizedMessage
                        })

                }, onFailureListener = {
                    toastMessageLiveData.value = it.localizedMessage
                })

            },
            onFailureListener = {

                toastMessageLiveData.value = it.localizedMessage
            })
    }


    fun getMessages() {

        getMessageCollectionRef(roomId = currentRoom?.roomId!!).orderBy(
            "messageDateTime",
            Query.Direction.ASCENDING
        )
            .addSnapshotListener { snapshots, ex ->

                if (ex != null) {

                    toastMessageLiveData.value = "error in get messages"
                } else {

                    val newMessages = mutableListOf<Message>()
                    for (dc in snapshots!!.documentChanges) {
                        when (dc.type) {
                            DocumentChange.Type.ADDED -> {

                                val message = dc.document.toObject(Message::class.java)
                                newMessages.add(message)

                            }
                        }
                    }
                    newMessagesList.value = newMessages
                }
            }
    }

}

