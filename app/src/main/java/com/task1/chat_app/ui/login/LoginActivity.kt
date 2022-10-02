package com.task1.chat_app.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.task1.chat_app.base.BaseActivity
import com.task1.chat_app.R
import com.task1.chat_app.databinding.ActivityLoginBinding
import com.task1.chat_app.ui.home.HomeActivity
import com.task1.chat_app.ui.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(), NavigatorLogin {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.loginViewModel = viewModel
        viewModel.navigator = this
    }

    override fun setLayout(): Int {

        return R.layout.activity_login
    }

    override fun setViewModel(): LoginViewModel {

        return ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun navigateToRegisterActivity() {

        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    override fun navigateToHomeActivity() {

        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}