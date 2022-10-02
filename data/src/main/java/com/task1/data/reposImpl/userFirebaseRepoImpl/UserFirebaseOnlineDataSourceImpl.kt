package com.task1.data.reposImpl.userFirebaseRepoImpl

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.task1.domain.repos.userFirebaseRepo.UserFirebaseOnlineDataSource
import kotlinx.coroutines.tasks.await

class UserFirebaseOnlineDataSourceImpl() : UserFirebaseOnlineDataSource {

    private val auth = Firebase.auth

    override suspend fun createFirebaseUser(email: String, password: String): AuthResult {


        try {

            return auth.createUserWithEmailAndPassword(email, password).await()

        } catch (ex: Exception) {
            throw ex
        }


    }

    override suspend fun signInFirebaseWithEmailAndPassword(
        email: String,
        password: String
    ): AuthResult {

        try {

            return auth.signInWithEmailAndPassword(email, password).await()

        } catch (ex: Exception) {
            throw ex
        }
    }


}