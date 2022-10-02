package com.task1.data.reposImpl.userRoomRepo

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.task1.data.Constants
import com.task1.domain.model.RoomUser
import com.task1.domain.repos.userRoomRepo.UserRoomOnlineDataSource
import kotlinx.coroutines.tasks.await

class UserRoomOnlineDataSourceImpl : UserRoomOnlineDataSource {
    override suspend fun addUserToRoom(currentUser: RoomUser, roomId: String) {

        try {

            val database = Firebase.firestore
            val roomCollection = database.collection(Constants.COLLECTION_ROOMS)
            val roomDocument = roomCollection.document(roomId)
            val roomsUserCollection = roomDocument.collection(Constants.COLLECTION_USERS_OF_ROOM)
            val roomUserDocument = roomsUserCollection.document(currentUser.userID!!)

            roomUserDocument.set(currentUser).await()


        } catch (ex: Exception) {
            throw ex
        }
    }

    override suspend fun checkUserExistenceInRoomUser(
        userID: String?,
        roomId: String
    ): DocumentSnapshot {


        try {

            val database = Firebase.firestore
            val roomCollection = database.collection(Constants.COLLECTION_ROOMS)
            val roomDocument =
                roomCollection.document(roomId).collection(Constants.COLLECTION_USERS_OF_ROOM)

            return roomDocument.document(userID!!).get().await()

        } catch (ex: Exception) {
            throw ex
        }
    }

    override suspend fun deleteUserFromRoomUser(userID: String?, roomId: String) {

        try {

            val database = Firebase.firestore
            val roomCollection = database.collection(Constants.COLLECTION_ROOMS)
            val roomDocument =
                roomCollection.document(roomId).collection(Constants.COLLECTION_USERS_OF_ROOM)

            val userDocument = roomDocument.document(userID!!).delete().await()

        } catch (ex: Exception) {
            throw ex
        }
    }


    override suspend fun getUsersOfRoomsFromFireStore(roomId: String): QuerySnapshot {

        try {

            val database = Firebase.firestore
            val roomCollection = database.collection(Constants.COLLECTION_ROOMS)
            val roomDocument = roomCollection.document(roomId)
            val usersOfRoom = roomDocument.collection(Constants.COLLECTION_USERS_OF_ROOM)


            return usersOfRoom.get().await()
        } catch (ex: Exception) {
            throw ex
        }


    }

    override suspend fun updateNumberOfUsers(roomId: String, numberOfUser: Int) {


        try {

            val database = Firebase.firestore
            val databaseCollectionRef = database.collection(Constants.COLLECTION_ROOMS)
            val roomDocument = databaseCollectionRef.document(roomId)
                .update("numberOfUsers", numberOfUser).await()
        } catch (ex: Exception) {
            throw ex
        }


    }


}