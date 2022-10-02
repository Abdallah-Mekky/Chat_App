package com.task1.chat_app.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.task1.chat_app.Constants
import com.task1.chat_app.R
import com.task1.chat_app.base.BaseActivity
import com.task1.chat_app.databinding.ActivityHomeBinding
import com.task1.chat_app.ui.addRoom.AddRoomActivity
import com.task1.chat_app.ui.chat.ChatActivity
import com.task1.chat_app.ui.login.LoginActivity
import com.task1.chat_app.ui.roomDetails.RoomDetailsActivity
import com.task1.domain.model.Room
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(), NavigatorHome {


    @Inject lateinit var roomsAdapter: RoomsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.homeViewModel = viewModel
        viewModel.navigator = this

        roomsAdapter.onItemClickListener = object : RoomsAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, room: Room) {

                viewModel.controlNavigationOfUser(room)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        subcribeToLiveData()
        viewModel.getRoomsList()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun setLayout(): Int {

        return R.layout.activity_home
    }

    override fun setViewModel(): HomeViewModel {

        return ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun openNavigationView() {

        viewDataBinding.drawerLayout.openDrawer(GravityCompat.START)
    }

    override fun navigateToAddRoomActivity() {

        val intent = Intent(this, AddRoomActivity::class.java)
        startActivity(intent)
    }

    override fun goToLoginActivity() {

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun openRoomDetailsActivity(room: Room) {

        val intent = Intent(this@HomeActivity, RoomDetailsActivity::class.java)
        intent.putExtra(Constants.ROOM_PUT_EXTRA, room)
        startActivity(intent)
    }

    override fun openChatActivity(room: Room) {

        val intent = Intent(this@HomeActivity, ChatActivity::class.java)
        intent.putExtra(Constants.ROOM_PUT_EXTRA, room)
        startActivity(intent)
    }


    fun subcribeToLiveData() {

        viewModel.roomsList.observe(this, Observer { rooms ->

            roomsAdapter.refreashAdapter(rooms)
            viewDataBinding.roomsRecyclerView.adapter = roomsAdapter
        })
    }

}