package com.task1.chat_app.ui.login

import androidx.databinding.ObservableField
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.task1.chat_app.DataUtils
import com.task1.chat_app.base.BaseViewModel
import com.task1.chat_app.database.checkUserExistence
import com.task1.chat_app.database.model.AppUser

class LoginViewModel : BaseViewModel<NavigatorLogin>() {

    var email = ObservableField<String>()
    var emailError = ObservableField<String>()
    var password = ObservableField<String>()
    var passwordError = ObservableField<String>()
    val auth = Firebase.auth


    fun login() {

        if (validate()) {

            loginWithUser()
        }
    }

    fun openRegisterActivity() {

        navigator?.navigateToRegisterActifity()
    }

    fun openHomeActivity() {

        navigator?.navigateToHomeActifity()
        clearFields()
    }

    fun clearFields() {
        email.set(null)
        password.set(null)
    }

    fun checkUserInFirestore(userID: String?) {

        checkUserExistence(userID, OnSuccessListener {

            progressDialogLiveData.value = false

            val currentUser = it.toObject(AppUser::class.java)

            if (currentUser != null) {

                DataUtils.currentUser = currentUser
                openHomeActivity()
            } else {

                progressDialogLiveData.value = false
                messageLiveData.value = "Not a user \nPlease Register"
                clearFields()
                return@OnSuccessListener
            }
        }, OnFailureListener {

            progressDialogLiveData.value = false
            messageLiveData.value = it.localizedMessage
        })

    }

    private fun loginWithUser() {

        progressDialogLiveData.value = true

        auth.signInWithEmailAndPassword(email.get()!!, password.get()!!)
            .addOnCompleteListener { result ->

                if (result.isSuccessful) {

                    progressDialogLiveData.value = false

                    checkUserInFirestore(result.result.user?.uid)

                } else {

                    progressDialogLiveData.value = false
                    messageLiveData.value = result.exception?.localizedMessage

                }
            }
    }

    private fun validate(): Boolean {

        var isVaild = true

        if (email.get().isNullOrBlank()) {

            emailError.set("Please Enter Email")
            isVaild = false
        } else {

            emailError.set(null)
        }

        if (password.get().isNullOrBlank()) {

            passwordError.set("Please Enter Password")
            isVaild = false
        } else {

            passwordError.set(null)
        }

        return isVaild
    }
}