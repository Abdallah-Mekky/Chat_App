package com.task1.chat_app.ui.splash

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.task1.chat_app.DataUtils
import com.task1.chat_app.base.BaseViewModel
import com.task1.chat_app.database.checkUserExistence
import com.task1.chat_app.database.model.AppUser

class SplashViewModel : BaseViewModel<NavigatorSplash>() {


    fun autoLogin() {

        var firebaseUser = Firebase.auth.currentUser

        if (firebaseUser == null) {

            navigator?.goToLoginActivity()

        } else {

            //retrive the data of this user from firestore
            checkUserExistence(firebaseUser.uid, onSuccessListener = { user ->

                var currentUser = user.toObject(AppUser::class.java)
                DataUtils.currentUser = currentUser
                navigator?.goToHomeActivity()

                Log.e("userrr", "" + currentUser)

            }, onFailureListener = {

                toastMessageLiveData.value = it.localizedMessage
            })
        }

    }


}