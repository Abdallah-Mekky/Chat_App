package com.task1.chat_app.ui.roomDetails

import android.content.Intent
import android.os.Bundle
import com.task1.chat_app.Constants
import com.task1.chat_app.R
import com.task1.chat_app.base.BaseActivity
import com.task1.chat_app.database.model.Room
import com.task1.chat_app.databinding.ActivityRoomDetailsBinding
import com.task1.chat_app.ui.chat.ChatActivity
import com.task1.chat_app.ui.home.HomeActivity


class RoomDetailsActivity : BaseActivity<ActivityRoomDetailsBinding, RoomDetailsViewModel>(), NavigatorRoomDetails {

    lateinit var roomFromIntent: Room
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.roomDetailsViewModel = viewModel
        backButton()
        roomFromIntent = intent.getParcelableExtra(Constants.ROOM_PUT_EXTRA)!!
        viewModel.currentRoom = roomFromIntent
        viewModel.navigator = this
    }

    override fun setLayout(): Int {

        return R.layout.activity_room_details
    }

    override fun setViewModel(): RoomDetailsViewModel {

        return RoomDetailsViewModel()
    }

    override fun navigateToChatActivity() {

        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra(Constants.ROOM_PUT_EXTRA, roomFromIntent)
        startActivity(intent)
        finish()
    }

    override fun navigateToLoginActivity() {

        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun backButton() {

        setSupportActionBar(viewDataBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}