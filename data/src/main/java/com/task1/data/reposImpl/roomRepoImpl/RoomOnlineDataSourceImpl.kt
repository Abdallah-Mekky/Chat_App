package com.task1.data.reposImpl.roomRepoImpl

import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.task1.data.Constants
import com.task1.domain.model.Room
import com.task1.domain.repos.roomRepo.RoomOnlineDataSource
import kotlinx.coroutines.tasks.await

class RoomOnlineDataSourceImpl : RoomOnlineDataSource {
    override suspend fun addRoomToFirestore(room: Room) {

        try {

            val database = Firebase.firestore
            val collectionRooms = database.collection(Constants.COLLECTION_ROOMS)
            val roomDoc = collectionRooms.document()
            room.roomId = roomDoc.id
            roomDoc.set(room).await()

        } catch (ex: Exception) {

            throw ex
        }


    }

    override suspend fun getRoomsFromFirestore(): QuerySnapshot {

        try {
            val database = Firebase.firestore
            val collectionRooms = database.collection(Constants.COLLECTION_ROOMS)
            return collectionRooms.get().await()

        } catch (ex: Exception) {

            throw ex
        }

    }
}