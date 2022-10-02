package com.task1.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Room(
    var roomId: String? = null,
    var roomName: String? = null,
    var roomCategoryId: String? = null,
    var roomDescription: String? = null,
    var numberOfUsers: Int? = 0
) : Parcelable {



    fun getImageFromId(): Int? {

        return CatgorySpinner.getImageById(roomCategoryId ?: "MUSIC").catgoryImage

    }

    fun getNumber(): String {

        return "$numberOfUsers   Members"
    }

}