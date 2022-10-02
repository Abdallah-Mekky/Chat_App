package com.task1.chat_app.ui.register

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.task1.chat_app.base.BaseViewModel
import com.task1.domain.model.AppUser
import com.task1.domain.repos.userFirebaseRepo.UserFirebaseRepo
import com.task1.domain.repos.userFirestoreRepo.UserFirestoreRepo
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    val userFirestoreRepo: UserFirestoreRepo,
    val userFirebaseRepo: UserFirebaseRepo
) : BaseViewModel<NavigatorRegister>() {

    var firstName = ObservableField<String>()
    var firstNameError = ObservableField<String>()
    var lastName = ObservableField<String>()
    var lastNameError = ObservableField<String>()
    var userName = ObservableField<String>()
    var userNameError = ObservableField<String>()
    var email = ObservableField<String>()
    var emailError = ObservableField<String>()
    var password = ObservableField<String>()
    var passwordError = ObservableField<String>()


    fun createAccount() {

        if (vaildiate()) {

            addUserToFirebase()
        }
    }

    private fun clearFileds() {

        firstName.set(null)
        lastName.set(null)
        userName.set(null)
        email.set(null)
        password.set(null)
    }

    fun openLoginActivity() {

        navigator?.navigateToLoginActivity()
    }

    suspend fun createFirestoreUser(userID: String?) {

        val currentUser =
            AppUser(userID, firstName.get(), lastName.get(), userName.get(), email.get())

        viewModelScope.launch {


            try {

                userFirestoreRepo.addUserToFirestore(currentUser)
                progressDialogLiveData.value = false
                openLoginActivity()

            } catch (ex: Exception) {

                progressDialogLiveData.value = false
                messageLiveData.value = ex.localizedMessage

            }

        }

    }

    private fun addUserToFirebase() {

        progressDialogLiveData.value = true

        viewModelScope.launch {

            try {

                val result = userFirebaseRepo.createFirebaseUser(email.get()!!, password.get()!!)
                progressDialogLiveData.value = false
                createFirestoreUser(result.user?.uid)
                clearFileds()

            } catch (ex: Exception) {

                progressDialogLiveData.value = false

                if (ex.localizedMessage == "The email address is already in use by another account.") {

                    messageLiveData.value = "Error in email address"
                } else {
                    messageLiveData.value = ex.localizedMessage
                }

            }

        }
    }

    private fun vaildiate(): Boolean {

        var isVaild = true

        if (firstName.get().isNullOrBlank()) {

            firstNameError.set("Please Enter First Name")
            isVaild = false

        } else {

            firstNameError.set(null)
        }

        if (lastName.get().isNullOrBlank()) {

            lastNameError.set("Please Enter Last Name")
            isVaild = false
        } else {

            lastNameError.set(null)
        }

        if (userName.get().isNullOrBlank()) {

            userNameError.set("Please Enter User Name")
            isVaild = false
        } else {
            userNameError.set(null)
        }

        if (email.get().isNullOrBlank()) {

            emailError.set("Please Enter Email")
            isVaild = false
        } else {
            emailError.set(null)
        }

        if (password.get().isNullOrBlank()) {

            passwordError.set("Please Enter password")
            isVaild = false
        } else {
            passwordError.set(null)
        }
        return isVaild
    }
}