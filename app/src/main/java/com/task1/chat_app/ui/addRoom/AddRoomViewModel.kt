package com.task1.chat_app.ui.addRoom

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.task1.chat_app.base.BaseViewModel
import com.task1.chat_app.database.addRoomToFirestore
import com.task1.chat_app.database.model.CatgorySpinner
import com.task1.chat_app.database.model.Room

class AddRoomViewModel : BaseViewModel<NavigatorAddRoom>() {


    var roomName = ObservableField<String>()
    var roomNameError = ObservableField<String>()
    var roomDescreption = ObservableField<String>()
    var roomDescreptionError = ObservableField<String>()
    var catgoriesList = CatgorySpinner.getCatgoriesList()
    var selectedItem = catgoriesList[0]
    var isAdded = MutableLiveData<Boolean>()


    fun createRoom() {

        if (valdiate()) {
            sendRoomToFirestore()
        }

    }

    private fun sendRoomToFirestore() {

        isAdded.value = false

        progressDialogLiveData.value = true

        var currentRoom = Room(
            roomName = roomName.get(),
            roomCategoryId = selectedItem.catgoryId,
            roomDescription = roomDescreption.get()
        )

        addRoomToFirestore(currentRoom, onSuccessListener = {

            progressDialogLiveData.value = false
            isAdded.value = true

        }, onFailureListener = {

            progressDialogLiveData.value = false
            messageLiveData.value = it.localizedMessage

        })
    }


    private fun valdiate(): Boolean {

        var isVaild = true

        if (roomName.get().isNullOrBlank()) {

            roomNameError.set("Please Enter Room Name")
            isVaild = false
        } else {

            roomNameError.set(null)
        }

        if (roomDescreption.get().isNullOrBlank()) {

            roomDescreptionError.set("Please Enter Room Descreption")
            isVaild = false
        } else {

            roomDescreptionError.set(null)
        }

        return isVaild
    }


}