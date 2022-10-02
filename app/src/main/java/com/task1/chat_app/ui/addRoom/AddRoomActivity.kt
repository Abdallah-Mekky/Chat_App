package com.task1.chat_app.ui.addRoom

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.task1.chat_app.R
import com.task1.chat_app.base.BaseActivity
import com.task1.chat_app.databinding.ActivityAddRoomBinding
import com.task1.domain.model.Room
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AddRoomActivity : BaseActivity<ActivityAddRoomBinding, AddRoomViewModel>(), NavigatorAddRoom {


    @Inject lateinit var catgorySpinnerAdapter: CatgorySpinnerAdapter
    lateinit var room: Room

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.addRoomViewModel = viewModel
        viewModel.navigator = this
        backButton()
        initSpinner()
        subcribeToLiveData()

    }

    override fun setLayout(): Int {

        return R.layout.activity_add_room
    }

    override fun setViewModel(): AddRoomViewModel {

        return ViewModelProvider(this).get(AddRoomViewModel::class.java)
    }

    fun initSpinner() {

        catgorySpinnerAdapter.refreashAdapter(viewModel.catgoriesList)
        viewDataBinding.spinner.setAdapter(catgorySpinnerAdapter)

        viewDataBinding.spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    postion: Int,
                    p3: Long
                ) {

                    viewModel.selectedItem = viewModel.catgoriesList[postion]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
    }

    fun subcribeToLiveData() {

        viewModel.isAdded.observe(this, Observer {

            if (it) {

                showDialog(
                    "Room Succefully Added", "OK",
                    { dialog, p1 ->
                        dialog?.dismiss()
                        finish()
                    }, cancelable = false
                )
            }
        })
    }

    fun backButton() {

        setSupportActionBar(viewDataBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}