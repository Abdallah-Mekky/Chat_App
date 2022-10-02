package com.task1.chat_app.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.task1.chat_app.R
import com.task1.chat_app.base.BaseActivity
import com.task1.chat_app.databinding.ActivitySplashBinding
import com.task1.chat_app.ui.home.HomeActivity
import com.task1.chat_app.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(), NavigatorSplash {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.navigator = this
        viewModel.autoLogin()
    }

    override fun goToHomeActivity() {

        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()

    }

    override fun goToLoginActivity() {

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun setLayout(): Int {

        return R.layout.activity_splash
    }

    override fun setViewModel(): SplashViewModel {

        return ViewModelProvider(this).get(SplashViewModel::class.java)
    }
}