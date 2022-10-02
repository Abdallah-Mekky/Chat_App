package com.task1.domain.repos.userFirebaseRepo

import com.google.firebase.auth.AuthResult

interface UserFirebaseOnlineDataSource {

    suspend fun createFirebaseUser(email: String, password: String): AuthResult
    suspend fun signInFirebaseWithEmailAndPassword(email: String, password: String): AuthResult


}