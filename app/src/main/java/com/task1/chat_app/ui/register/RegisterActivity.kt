package com.task1.chat_app.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.task1.chat_app.base.BaseActivity
import com.task1.chat_app.R
import com.task1.chat_app.databinding.ActivityRegisterBinding
import com.task1.chat_app.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>(),
    NavigatorRegister {


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

        return ViewModelProvider(this).get(RegisterViewModel::class.java)
    }

    override fun navigateToLoginActivity() {

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    fun backButton() {

        setSupportActionBar(viewDataBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}