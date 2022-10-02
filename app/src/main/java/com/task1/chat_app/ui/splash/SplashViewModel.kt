package com.task1.chat_app.ui.splash

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.task1.chat_app.DataUtils
import com.task1.chat_app.base.BaseViewModel
import com.task1.domain.model.AppUser
import com.task1.domain.repos.userFirestoreRepo.UserFirestoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(val userFirestoreRepo: UserFirestoreRepo) :
    BaseViewModel<NavigatorSplash>() {


    fun autoLogin() {

        val firebaseUser = Firebase.auth.currentUser

        if (firebaseUser == null) {

            navigator?.goToLoginActivity()

        } else {

            viewModelScope.launch {

                try {

                    val currentUser = userFirestoreRepo.checkUserExistence(firebaseUser.uid)
                        .toObject(AppUser::class.java)
                    DataUtils.currentUser = currentUser
                    Log.e("user", "" + currentUser)

                    if (currentUser != null) {
                        navigator?.goToHomeActivity()
                    } else {
                        navigator?.goToLoginActivity()
                    }

                } catch (ex: Exception) {

                    toastMessageLiveData.value = ex.localizedMessage

                }
            }
        }

    }


}