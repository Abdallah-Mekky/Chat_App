package com.task1.chat_app.ui.chat

import android.os.Bundle
import android.view.Menu
import androidx.lifecycle.ViewModelProvider
import com.task1.chat_app.Constants
import com.task1.chat_app.R
import com.task1.chat_app.base.BaseActivity
import com.task1.chat_app.databinding.ActivityChatBinding
import com.task1.domain.model.Room
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChatActivity : BaseActivity<ActivityChatBinding, ChatViewModel>(), NavigatorChat {


    @Inject lateinit var chatAdapter: ChatAdapter
    lateinit var roomFromIntent: Room


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        backButton()
        viewDataBinding.chatViewModel = viewModel
        viewModel.navigator = this
        roomFromIntent = intent.getParcelableExtra(Constants.ROOM_PUT_EXTRA)!!
        viewModel.currentRoom = roomFromIntent

        subcribeToLiveData()
        viewModel.getMessages()
        viewDataBinding.messagesRecyclerView.adapter = chatAdapter

        deleteUserFromFirestore()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.leave_room_menu, menu)
        return true
    }


    override fun setLayout(): Int {

        return R.layout.activity_chat
    }

    override fun setViewModel(): ChatViewModel {

        return ViewModelProvider(this).get(ChatViewModel::class.java)
    }

    fun deleteUserFromFirestore() {

        viewDataBinding.toolbar.setOnMenuItemClickListener {

            if (it.itemId == R.id.leaveRoom) {

                viewModel.deleteUser()
            }

            return@setOnMenuItemClickListener true
        }
    }


    fun subcribeToLiveData() {

        viewModel.newMessagesList.observe(this) { newMessages ->
            chatAdapter.refreashAdapter(newMessages)
            viewDataBinding.messagesRecyclerView.smoothScrollToPosition(chatAdapter.itemCount)
        }
    }

    fun backButton() {

        setSupportActionBar(viewDataBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}