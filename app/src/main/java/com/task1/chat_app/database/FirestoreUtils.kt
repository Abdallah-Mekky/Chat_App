package com.task1.chat_app.database

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.task1.chat_app.Constants
import com.task1.chat_app.database.model.AppUser
import com.task1.chat_app.database.model.Message
import com.task1.chat_app.database.model.Room
import com.task1.chat_app.database.model.RoomUser


fun openDatabase(collectionName: String): CollectionReference {

    val database = Firebase.firestore
    return database.collection(collectionName)
}

fun addUserToFirestore(
    currentUser: AppUser,
    onSuccessListener: OnSuccessListener<Void>,
    onFailureListener: OnFailureListener
) {

    val user = openDatabase(Constants.COLLECTION_USERS)
    val userDocument = user.document(currentUser.userID!!)
    userDocument.set(currentUser)
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}

fun checkUserExistence(
    userID: String?,
    onSuccessListener: OnSuccessListener<DocumentSnapshot>,
    onFailureListener: OnFailureListener
) {

    val collectionRef = openDatabase(Constants.COLLECTION_USERS)
    val userDocument = collectionRef.document(userID!!).get()
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}

fun addRoomToFirestore(
    room: Room,
    onSuccessListener: OnSuccessListener<Void>,
    onFailureListener: OnFailureListener
) {

    val collectionRef = openDatabase(Constants.COLLECTION_ROOMS)
    val roomDoc = collectionRef.document()

    room.roomId = roomDoc.id

    roomDoc.set(room)
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)

}


fun getRoomsFromFirestore(
    onSuccessListener: OnSuccessListener<QuerySnapshot>,
    onFailureListener: OnFailureListener
) {

    val collectionRef = openDatabase(Constants.COLLECTION_ROOMS)

    collectionRef.get()
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}


fun addUserToRoom(
    currentUser: RoomUser,
    roomId: String,
    onSuccessListener: OnSuccessListener<Void>,
    onFailureListener: OnFailureListener
) {


    val roomCollection = openDatabase(Constants.COLLECTION_ROOMS)
    val roomDocument = roomCollection.document(roomId)
    val roomsUserCollection = roomDocument.collection(Constants.COLLECTION_USERS_OF_ROOM)
    val roomUserDocument = roomsUserCollection.document(currentUser.userID!!)

    roomUserDocument.set(currentUser)
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)


}

fun checkUserExistenceInRoomUser(
    userID: String?,
    roomId: String,
    onSuccessListener: OnSuccessListener<DocumentSnapshot>,
    onFailureListener: OnFailureListener
) {

    val collectionRef = openDatabase(Constants.COLLECTION_ROOMS)
    val roomDocument = collectionRef.document(roomId).collection(Constants.COLLECTION_USERS_OF_ROOM)

    val userDocument = roomDocument.document(userID!!).get()
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}

fun deleteUserFromRoomUser(
    userID: String?,
    roomId: String,
    onSuccessListener: OnSuccessListener<Void>,
    onFailureListener: OnFailureListener
) {

    val collectionRef = openDatabase(Constants.COLLECTION_ROOMS)
    val roomDocument = collectionRef.document(roomId).collection(Constants.COLLECTION_USERS_OF_ROOM)

    val userDocument = roomDocument.document(userID!!).delete()
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}


fun addMessageToFirestore(
    message: Message,
    roomId: String,
    onSuccessListener: OnSuccessListener<Void>,
    onFailureListener: OnFailureListener
) {

    val roomCollectionRef = openDatabase(Constants.COLLECTION_ROOMS)
    val roomDocument = roomCollectionRef.document(roomId)
    val messageCollectionRef = roomDocument.collection(Constants.COLLECTION_MESSAGES)
    val messageDocument = messageCollectionRef.document()

    message.id = messageDocument.id

    messageDocument.set(message)
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}


fun getMessageCollectionRef(roomId: String): CollectionReference {

    val roomCollectionRef = openDatabase(Constants.COLLECTION_ROOMS)
    val roomDocument = roomCollectionRef.document(roomId)
    val messageCollectionRef = roomDocument.collection(Constants.COLLECTION_MESSAGES)

    return messageCollectionRef
}

fun updateNumberOfUsers(
    roomId: String,
    numberOfUser: Int,
    onSuccessListener: OnSuccessListener<Void>,
    onFailureListener: OnFailureListener
) {

    val roomCollectionRef = openDatabase(Constants.COLLECTION_ROOMS)
    val roomDocument = roomCollectionRef.document(roomId)
        .update("numberOfUsers", numberOfUser)
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)


}

fun getUsersOfRoomsFromFireStore(
    roomId: String,
    onSuccessListener: OnSuccessListener<QuerySnapshot>,
    onFailureListener: OnFailureListener
) {

    val roomCollectionRef = openDatabase(Constants.COLLECTION_ROOMS)
    val roomDocument = roomCollectionRef.document(roomId)
    val usersOfRoom = roomDocument.collection(Constants.COLLECTION_USERS_OF_ROOM)

    usersOfRoom.get()
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}


