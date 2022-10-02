package com.task1.domain.repos.messageRepo

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ListenerRegistration
import com.task1.domain.model.Message

interface MessageOnlineDataSource {

    suspend fun addMessageToFirestore(message: Message, roomId: String)

    suspend fun getMessageCollectionRef(roomId: String): CollectionReference

    suspend fun getMessages(
        collectionReference: CollectionReference,
        newMessagesList: MutableLiveData<MutableList<Message>>,
        errorMessage: MutableLiveData<String>
    ): ListenerRegistration


}