package com.task1.chat_app.ui.login

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.task1.chat_app.DataUtils
import com.task1.chat_app.base.BaseViewModel
import com.task1.domain.model.AppUser
import com.task1.domain.repos.userFirebaseRepo.UserFirebaseRepo
import com.task1.domain.repos.userFirestoreRepo.UserFirestoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val userFirebaseRepo: UserFirebaseRepo,
    val userFirestoreRepo: UserFirestoreRepo
) : BaseViewModel<NavigatorLogin>() {

    var email = ObservableField<String>()
    var emailError = ObservableField<String>()
    var password = ObservableField<String>()
    var passwordError = ObservableField<String>()

    fun login() {

        if (validate()) {

            loginWithUser()
        }
    }

    fun openRegisterActivity() {

        navigator?.navigateToRegisterActivity()
    }

    fun openHomeActivity() {

        navigator?.navigateToHomeActivity()
        clearFields()
    }

    fun clearFields() {
        email.set(null)
        password.set(null)
    }

    fun checkUserInFirestore(userID: String?) {

        viewModelScope.launch {

            try {

                val result =
                    userFirestoreRepo.checkUserExistence(userID).toObject(AppUser::class.java)
                progressDialogLiveData.value = false

                if (result != null) {

                    DataUtils.currentUser = result
                    openHomeActivity()
                } else {

                    progressDialogLiveData.value = false
                    messageLiveData.value = "Not a user \nPlease Register"
                    clearFields()
                }

            } catch (ex: Exception) {

                progressDialogLiveData.value = false
                messageLiveData.value = ex.localizedMessage
            }
        }
    }

    private fun loginWithUser() {

        progressDialogLiveData.value = true

        viewModelScope.launch {

            try {

                val result = userFirebaseRepo.signInFirebaseWithEmailAndPassword(
                    email.get()!!,
                    password.get()!!
                )
                progressDialogLiveData.value = false
                checkUserInFirestore(result.user?.uid)

            } catch (ex: Exception) {

                progressDialogLiveData.value = false
                messageLiveData.value = ex.localizedMessage
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