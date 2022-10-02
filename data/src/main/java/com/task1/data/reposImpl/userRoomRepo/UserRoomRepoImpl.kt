package com.task1.data.reposImpl.userRoomRepo

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.task1.domain.model.RoomUser
import com.task1.domain.repos.userRoomRepo.UserRoomOnlineDataSource
import com.task1.domain.repos.userRoomRepo.UserRoomRepo

class UserRoomRepoImpl(private val userRoomOnlineDataSource: UserRoomOnlineDataSource) :
    UserRoomRepo {

    override suspend fun addUserToRoom(currentUser: RoomUser, roomId: String) {

        try {

            userRoomOnlineDataSource.addUserToRoom(currentUser, roomId)

        } catch (ex: Exception) {
            throw ex
        }
    }

    override suspend fun checkUserExistenceInRoomUser(
        userID: String?,
        roomId: String
    ): DocumentSnapshot {

        try {

            return userRoomOnlineDataSource.checkUserExistenceInRoomUser(userID, roomId)

        } catch (ex: Exception) {
            throw ex
        }

    }

    override suspend fun deleteUserFromRoomUser(userID: String?, roomId: String) {

        try {

            userRoomOnlineDataSource.deleteUserFromRoomUser(userID, roomId)

        } catch (ex: Exception) {
            throw ex
        }
    }


    override suspend fun getUsersOfRoomsFromFireStore(roomId: String): QuerySnapshot {

        try {

            return userRoomOnlineDataSource.getUsersOfRoomsFromFireStore(roomId)

        } catch (ex: Exception) {
            throw ex
        }
    }

    override suspend fun updateNumberOfUsers(roomId: String, numberOfUser: Int) {

        try {
            userRoomOnlineDataSource.updateNumberOfUsers(roomId, numberOfUser)

        } catch (ex: Exception) {
            throw ex
        }
    }


}