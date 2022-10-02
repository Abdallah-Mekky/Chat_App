package com.task1.data.reposImpl.messageRepoImpl

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.task1.data.Constants
import com.task1.domain.model.Message
import com.task1.domain.repos.messageRepo.MessageOnlineDataSource
import kotlinx.coroutines.tasks.await

class MessageOnlineDataSourceImpl() : MessageOnlineDataSource {
    override suspend fun addMessageToFirestore(message: Message, roomId: String) {

        try {

            val database = Firebase.firestore
            val roomCollectionRef = database.collection(Constants.COLLECTION_ROOMS)
            val roomDocument = roomCollectionRef.document(roomId)
            val messageCollectionRef = roomDocument.collection(Constants.COLLECTION_MESSAGES)
            val messageDocument = messageCollectionRef.document()

            message.id = messageDocument.id

            messageDocument.set(message).await()

        } catch (ex: Exception) {
            throw ex
        }
    }

    override suspend fun getMessageCollectionRef(roomId: String): CollectionReference {

        try {

            val database = Firebase.firestore
            val roomCollectionRef = database.collection(Constants.COLLECTION_ROOMS)
            val roomDocument = roomCollectionRef.document(roomId)

            return roomDocument.collection(Constants.COLLECTION_MESSAGES)

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

            return collectionReference.orderBy("messageDateTime", Query.Direction.ASCENDING)
                .addSnapshotListener { snapshots, ex ->

                    if (ex != null) {

                        errorMessage.value = "error in get messages"

                    } else {

                        val newMessages = mutableListOf<Message>()
                        for (dc in snapshots!!.documentChanges) {
                            when (dc.type) {
                                DocumentChange.Type.ADDED -> {

                                    val message = dc.document.toObject(Message::class.java)
                                    newMessages.add(message)

                                }
                            }
                        }
                        newMessagesList.value = newMessages
                    }
                }

        } catch (ex: Exception) {

            throw ex
        }

    }


}