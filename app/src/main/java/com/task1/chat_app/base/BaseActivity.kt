package com.task1.chat_app.base

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

abstract class BaseActivity<DataBinding : ViewDataBinding, ViewModel : BaseViewModel<*>> :
    AppCompatActivity() {

    lateinit var viewDataBinding: DataBinding
    lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding = DataBindingUtil.setContentView(this, setLayout())
        viewModel = ViewModelProvider(this).get(setViewModel()::class.java)
        subscribeToLiveData()
    }


    abstract fun setLayout(): Int

    abstract fun setViewModel(): ViewModel

    var alertDialog: AlertDialog? = null
    var progressDialog: ProgressDialog? = null
    var defaultAction = DialogInterface.OnClickListener { dialog, p1 -> dialog?.dismiss() }

    fun showDialog(
        message: String,
        posActionName: String? = null,
        posAction: DialogInterface.OnClickListener? = null,
        negActionName: String? = null,
        negAction: DialogInterface.OnClickListener? = null,
        cancelable: Boolean = true
    ) {

        var bulider = AlertDialog.Builder(this)

        bulider.setMessage(message)

        if (posActionName != null) {

            bulider.setPositiveButton(posActionName, posAction ?: defaultAction)
        }

        if (negActionName != null) {

            bulider.setNegativeButton(negActionName, negAction ?: defaultAction)
        }
        bulider.setCancelable(cancelable)

        bulider.show()

    }


    fun showProgressDialog() {

        progressDialog = ProgressDialog(this)
        progressDialog?.setMessage("Loading....")
        progressDialog?.setCancelable(false)
        progressDialog?.show()
    }

    fun hideProgressDialog() {

        progressDialog?.dismiss()
        progressDialog = null
    }

    fun showToast(message: String) {

        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun subscribeToLiveData() {

        viewModel.messageLiveData.observe(this, Observer {


            showDialog("" + it, "ok")

        })

        viewModel.messageLeaveRoomLiveData.observe(this, Observer {

            if (it == "User Successfully Deleted From This Room") {

                showDialog(it, "OK", { dialog, p1 ->
                    dialog?.dismiss()
                    finish()
                }, cancelable = false)
            } else {

                showDialog(it, "OK", { dialog, p1 ->
                    dialog?.dismiss()
                    finish()
                })
            }
        })

        viewModel.toastMessageLiveData.observe(this, Observer {

            showToast(it)
        })

        viewModel.progressDialogLiveData.observe(this, Observer {

            if (it == true) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }

        })

    }


}