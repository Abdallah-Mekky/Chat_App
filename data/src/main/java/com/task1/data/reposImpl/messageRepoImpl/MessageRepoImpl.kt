package com.task1.data.reposImpl.messageRepoImpl

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ListenerRegistration
import com.task1.domain.model.Message
import com.task1.domain.repos.messageRepo.MessageOnlineDataSource
import com.task1.domain.repos.messageRepo.MessageRepo

class MessageRepoImpl(private val messageOnlineDataSource: MessageOnlineDataSource) : MessageRepo {
    override suspend fun addMessageToFirestore(message: Message, roomId: String) {

        try {

            messageOnlineDataSource.addMessageToFirestore(message, roomId)

        } catch (ex: Exception) {
            throw ex
        }
    }

    override suspend fun getMessageCollectionRef(roomId: String): CollectionReference {

        try {

            return messageOnlineDataSource.getMessageCollectionRef(roomId)

        } catch (ex: Exception) {
            throw ex
        }
    }

    override suspend fun getMessages(
        collectionReference: CollectionReference,
        newMessagesList: MutableLiveData<MutableList<Message>>,
        errorMessage: MutableLiveData<String>
    ): ListenerRegistration {

        try {

            return messageOnlineDataSource.getMessages(
                collectionReference,
                newMessagesList,
                errorMessage
            )

        } catch (ex: Exception) {
            throw ex
        }

    }


}