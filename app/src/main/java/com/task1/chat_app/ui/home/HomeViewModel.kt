package com.task1.chat_app.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.task1.chat_app.DataUtils
import com.task1.chat_app.base.BaseViewModel
import com.task1.domain.model.Room
import com.task1.domain.model.RoomUser
import com.task1.domain.repos.roomRepo.RoomRepo
import com.task1.domain.repos.userRoomRepo.UserRoomRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val userRoomRepo: UserRoomRepo, val roomRepo: RoomRepo) :
    BaseViewModel<NavigatorHome>() {


    var roomsListMutableLiveData = MutableLiveData<List<Room>>()
    var roomsList: LiveData<List<Room>> = roomsListMutableLiveData
    var currentUser = DataUtils.currentUser


    fun controlNavigationView() {
        navigator?.openNavigationView()
    }

    fun openAddRoomActivity() {
        navigator?.navigateToAddRoomActivity()
    }

    fun openLoginActivity() {

        navigator?.goToLoginActivity()
    }

    fun getRoomsList() {

        progressDialogLiveData.value = true

        viewModelScope.launch {

            try {

                roomsListMutableLiveData.value =
                    roomRepo.getRoomsFromFirestore().toObjects(Room::class.java)
                progressDialogLiveData.value = false

            } catch (ex: Exception) {

                progressDialogLiveData.value = false
                messageLiveData.value = ex.localizedMessage
            }

        }

    }

    fun logout() {

        Firebase.auth.signOut()
        openLoginActivity()
        DataUtils.currentUser = null
        Log.e("current User", "" + DataUtils.currentUser)
    }


    fun controlNavigationOfUser(room: Room) {

        viewModelScope.launch {

            try {

                val user = userRoomRepo.checkUserExistenceInRoomUser(
                    DataUtils.currentUser?.userID,
                    room.roomId!!
                )
                    .toObject(RoomUser::class.java)

                if (user != null) {
                    navigator?.openChatActivity(room)
                } else {

                    navigator?.openRoomDetailsActivity(room)
                }

            } catch (ex: Exception) {

                toastMessageLiveData.value = "fail"
            }
        }
    }
}