package com.task1.chat_app.ui.register

import android.content.Intent
import android.os.Bundle
import com.task1.chat_app.base.BaseActivity
import com.task1.chat_app.R
import com.task1.chat_app.databinding.ActivityRegisterBinding
import com.task1.chat_app.ui.login.LoginActivity

class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>(), NavigatorRegister {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.registerViewModel = viewModel
        viewModel.navigator = this
        backButton()
    }

    override fun setLayout(): Int {

        return R.layout.activity_register
    }

    override fun setViewModel(): RegisterViewModel {

        return RegisterViewModel()
    }

    override fun navigateToLoginActifity() {

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    fun backButton() {

        setSupportActionBar(viewDataBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}