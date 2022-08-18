package com.task1.chat_app.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel<N> : ViewModel() {

    var messageLiveData = MutableLiveData<String>()
    var messageLeaveRoomLiveData = MutableLiveData<String>()
    var progressDialogLiveData = MutableLiveData<Boolean>()
    var toastMessageLiveData = MutableLiveData<String>()
    var navigator: N? = null
}