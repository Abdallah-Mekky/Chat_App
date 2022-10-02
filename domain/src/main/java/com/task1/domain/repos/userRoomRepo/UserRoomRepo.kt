package com.task1.domain.repos.userRoomRepo

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.task1.domain.model.RoomUser

interface UserRoomRepo {

    suspend fun addUserToRoom(currentUser: RoomUser, roomId: String)
    suspend fun checkUserExistenceInRoomUser(userID: String?, roomId: String): DocumentSnapshot
    suspend fun deleteUserFromRoomUser(userID: String?, roomId: String)
    suspend fun getUsersOfRoomsFromFireStore(roomId: String): QuerySnapshot
    suspend fun updateNumberOfUsers(roomId: String, numberOfUser: Int)

}

