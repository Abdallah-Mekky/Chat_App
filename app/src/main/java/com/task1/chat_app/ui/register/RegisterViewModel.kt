package com.task1.chat_app.ui.register

import androidx.databinding.ObservableField
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.task1.chat_app.base.BaseViewModel
import com.task1.chat_app.database.addUserToFirestore
import com.task1.chat_app.database.model.AppUser

class RegisterViewModel : BaseViewModel<NavigatorRegister>() {

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
    val auth = Firebase.auth



    fun createAccount(){

        if(vaildiate()){

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

    fun openLoginActivity(){

        navigator?.navigateToLoginActifity()
    }

    fun createFirestoreUser(userID:String?){

        val currentUser = AppUser(userID,firstName.get(),lastName.get(),userName.get(),email.get())

        addUserToFirestore(currentUser, OnSuccessListener {

            progressDialogLiveData.value = false
            openLoginActivity()
        }, OnFailureListener {

            progressDialogLiveData.value = false
            messageLiveData.value = it.localizedMessage
            return@OnFailureListener
        })
    }

    private fun addUserToFirebase() {

        progressDialogLiveData.value = true

        auth.createUserWithEmailAndPassword(email.get()!!,password.get()!!).addOnCompleteListener{ result ->


            if(result.isSuccessful){

                progressDialogLiveData.value = false

                createFirestoreUser(result.result.user?.uid)
                clearFileds()
            }
            else{

                progressDialogLiveData.value = false

                if(result.exception?.localizedMessage == "The email address is already in use by another account."){

                    messageLiveData.value = "Error in email address"
                }else{
                    messageLiveData.value = result.exception?.localizedMessage
                }
            }
        }
    }

    private fun vaildiate(): Boolean {

        var isVaild = true

        if(firstName.get().isNullOrBlank()){

            firstNameError.set("Please Enter First Name")
            isVaild = false

        }

        else{

            firstNameError.set(null)
        }

        if(lastName.get().isNullOrBlank()){

            lastNameError.set("Please Enter Last Name")
            isVaild = false
        }

        else{

            lastNameError.set(null)
        }

        if(userName.get().isNullOrBlank()){

            userNameError.set("Please Enter User Name")
            isVaild = false
        }

        else{
            userNameError.set(null)
        }

        if(email.get().isNullOrBlank()){

            emailError.set("Please Enter Email")
            isVaild = false
        }

        else{
            emailError.set(null)
        }

        if(password.get().isNullOrBlank()){

            passwordError.set("Please Enter password")
            isVaild = false
        }
        else{
            passwordError.set(null)
        }
        return isVaild
    }
}