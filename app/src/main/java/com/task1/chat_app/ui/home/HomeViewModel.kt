package com.task1.chat_app.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.task1.chat_app.DataUtils
import com.task1.chat_app.base.BaseViewModel
import com.task1.chat_app.database.checkUserExistenceInRoomUser
import com.task1.chat_app.database.getRoomsFromFirestore
import com.task1.chat_app.database.model.Room
import com.task1.chat_app.database.model.RoomUser

class HomeViewModel : BaseViewModel<NavigatorHome>() {


    var roomsList = MutableLiveData<List<Room>>()
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

        getRoomsFromFirestore(onSuccessListener = {
            progressDialogLiveData.value = false
            roomsList.value = it.toObjects(Room::class.java)
        },
            onFailureListener = {

                progressDialogLiveData.value = false
                messageLiveData.value = it.localizedMessage
            })
    }

    fun logout() {

        Firebase.auth.signOut()
        openLoginActivity()
        DataUtils.currentUser = null
        Log.e("current User", "" + DataUtils.currentUser)
    }


    fun controlNavigationOfUser(room: Room) {

        checkUserExistenceInRoomUser(
            DataUtils.currentUser?.userID,
            roomId = room.roomId!!,
            onSuccessListener = {

                var user = it.toObject(RoomUser::class.java)
                if (user != null) {
                    navigator?.openChatActivity(room)
                } else {

                    navigator?.openRoomDetailsActivity(room)
                }
            },
            onFailureListener = {

                toastMessageLiveData.value = "fail"
            })
    }
}