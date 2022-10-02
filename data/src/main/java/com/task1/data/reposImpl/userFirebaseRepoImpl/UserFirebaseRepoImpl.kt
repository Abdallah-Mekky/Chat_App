package com.task1.data.reposImpl.userFirebaseRepoImpl

import com.google.firebase.auth.AuthResult
import com.task1.domain.repos.userFirebaseRepo.UserFirebaseOnlineDataSource
import com.task1.domain.repos.userFirebaseRepo.UserFirebaseRepo


class UserFirebaseRepoImpl(private val userFirebaseOnlineDataSource: UserFirebaseOnlineDataSource) :
    UserFirebaseRepo {
    override suspend fun createFirebaseUser(email: String, password: String): AuthResult {

        try {

            return userFirebaseOnlineDataSource.createFirebaseUser(email, password)

        } catch (ex: Exception) {
            throw ex
        }
    }

    override suspend fun signInFirebaseWithEmailAndPassword(
        email: String,
        password: String
    ): AuthResult {

        try {

            return userFirebaseOnlineDataSource.signInFirebaseWithEmailAndPassword(email, password)

        } catch (ex: Exception) {
            throw ex
        }
    }


}