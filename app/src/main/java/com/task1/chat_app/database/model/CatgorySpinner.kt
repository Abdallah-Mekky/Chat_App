package com.task1.chat_app.database.model

import com.task1.chat_app.R

data class CatgorySpinner(
    var catgoryId: String? = null,
    var catgoryName: String? = null,
    var catgoryImage: Int? = null
) {

    companion object {

        fun getImageById(catgoryId: String): CatgorySpinner {

            val catgoryId1 = "MOVIES"
            val catgoryId2 = "CODING"
            val catgoryId3 = "SPORT"



            when (catgoryId) {

                catgoryId1 -> {

                    return CatgorySpinner(catgoryId1, "Movies", R.drawable.movies)
                }

                catgoryId2 -> {

                    return CatgorySpinner(catgoryId2, "Coding", R.drawable.coding)
                }

                else -> {

                    return CatgorySpinner(catgoryId3, "Sport", R.drawable.sports)
                }

            }
        }


        fun getCatgoriesList(): List<CatgorySpinner> {

            var catgoriesList = listOf(

                getImageById("MOVIES"),
                getImageById("CODING"),
                getImageById("SPORT")
            )


            return catgoriesList
        }

    }
}