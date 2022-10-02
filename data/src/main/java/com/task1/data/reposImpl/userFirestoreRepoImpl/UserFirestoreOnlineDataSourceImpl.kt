package com.task1.data.reposImpl.userFirestoreRepoImpl


import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.task1.data.Constants
import com.task1.domain.model.AppUser
import com.task1.domain.repos.userFirestoreRepo.UserFirestoreOnlineDataSource
import kotlinx.coroutines.tasks.await

class UserFirestoreOnlineDataSourceImpl() : UserFirestoreOnlineDataSource {


    override suspend fun addUserToFirestore(currentUser: AppUser) {

        try {
            val database = Firebase.firestore
            val databaseCollectionRef = database.collection(Constants.COLLECTION_USERS)
            val userDocument = databaseCollectionRef.document(currentUser.userID!!)
            userDocument.set(currentUser).await()
        } catch (ex: Exception) {
            throw ex
        }

    }

    override suspend fun checkUserExistence(userID: String?): DocumentSnapshot {

        try {

            val database = Firebase.firestore
            val databaseCollectionRef = database.collection(Constants.COLLECTION_USERS)
            val userDocument = databaseCollectionRef.document(userID!!)

            return userDocument.get().await()
        } catch (ex: Exception) {
            throw ex
        }

    }


}