package com.task1.domain.repos.userFirestoreRepo

import com.google.firebase.firestore.DocumentSnapshot
import com.task1.domain.model.AppUser

interface UserFirestoreOnlineDataSource {

    suspend fun addUserToFirestore(currentUser: AppUser)
    suspend fun checkUserExistence(userID: String?): DocumentSnapshot

}