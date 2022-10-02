package com.task1.data.reposImpl.userFirestoreRepoImpl

import com.google.firebase.firestore.DocumentSnapshot
import com.task1.domain.model.AppUser
import com.task1.domain.repos.userFirestoreRepo.UserFirestoreOnlineDataSource
import com.task1.domain.repos.userFirestoreRepo.UserFirestoreRepo

class UserFirestoreRepoImpl(private val userFirestoreOnlineDataSource: UserFirestoreOnlineDataSource) :
    UserFirestoreRepo {

    override suspend fun addUserToFirestore(currentUser: AppUser) {

        try {

            userFirestoreOnlineDataSource.addUserToFirestore(currentUser)
        } catch (ex: Exception) {
            throw ex
        }
    }

    override suspend fun checkUserExistence(userID: String?): DocumentSnapshot {

        try {

            return userFirestoreOnlineDataSource.checkUserExistence(userID)
        } catch (ex: Exception) {
            throw ex
        }
    }


}