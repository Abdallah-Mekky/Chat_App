package com.task1.chat_app.ui.addRoom

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.task1.chat_app.base.BaseViewModel
import com.task1.domain.model.CatgorySpinner
import com.task1.domain.model.Room
import com.task1.domain.repos.roomRepo.RoomRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddRoomViewModel @Inject constructor(private val roomRepo: RoomRepo,
val catgorySpinner: CatgorySpinner) : BaseViewModel<NavigatorAddRoom>() {


    var roomName = ObservableField<String>()
    var roomNameError = ObservableField<String>()
    var roomDescreption = ObservableField<String>()
    var roomDescreptionError = ObservableField<String>()
    var catgoriesList = catgorySpinner.getCatgoriesList()
    var selectedItem = catgoriesList[0]
    var isAddedMutableLiveData = MutableLiveData<Boolean>()
    var isAdded: LiveData<Boolean> = isAddedMutableLiveData


    fun createRoom() {

        if (valdiate()) {
            sendRoomToFirestore()
        }

    }

    private fun sendRoomToFirestore() {

        isAddedMutableLiveData.value = false

        progressDialogLiveData.value = true

        var currentRoom = Room(
            roomName = roomName.get(),
            roomCategoryId = selectedItem.catgoryId,
            roomDescription = roomDescreption.get()
        )

        viewModelScope.launch {

            try {

                roomRepo.addRoomToFirestore(currentRoom)
                progressDialogLiveData.value = false
                isAddedMutableLiveData.value = true


            } catch (ex: Exception) {

                progressDialogLiveData.value = false
                messageLiveData.value = ex.localizedMessage
            }

        }
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