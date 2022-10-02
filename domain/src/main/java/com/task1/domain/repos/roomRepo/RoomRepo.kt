package com.task1.domain.repos.roomRepo

import com.google.firebase.firestore.QuerySnapshot
import com.task1.domain.model.Room

interface RoomRepo {

    suspend fun addRoomToFirestore(room: Room)
    suspend fun getRoomsFromFirestore(): QuerySnapshot

}


