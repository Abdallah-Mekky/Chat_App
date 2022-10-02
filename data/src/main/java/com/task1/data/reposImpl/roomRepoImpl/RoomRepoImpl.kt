package com.task1.data.reposImpl.roomRepoImpl

import com.google.firebase.firestore.QuerySnapshot
import com.task1.domain.model.Room
import com.task1.domain.repos.roomRepo.RoomOnlineDataSource
import com.task1.domain.repos.roomRepo.RoomRepo

class RoomRepoImpl(private val roomOnlineDataSource: RoomOnlineDataSource) : RoomRepo {
    override suspend fun addRoomToFirestore(room: Room) {

        try {

            roomOnlineDataSource.addRoomToFirestore(room)

        } catch (ex: Exception) {

            throw ex
        }
    }

    override suspend fun getRoomsFromFirestore(): QuerySnapshot {

        try {

            return roomOnlineDataSource.getRoomsFromFirestore()

        } catch (ex: Exception) {

            throw ex

        }
    }
}