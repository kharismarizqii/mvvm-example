package com.kharismarizqii.mvvmtutorial.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kharismarizqii.mvvmtutorial.R
import com.kharismarizqii.mvvmtutorial.databinding.ActivityLoginBinding
import com.kharismarizqii.mvvmtutorial.util.hide
import com.kharismarizqii.mvvmtutorial.util.show
import com.kharismarizqii.mvvmtutorial.util.toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), AuthListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.authListener = this
    }

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSuccess(loginResponse: LiveData<String>) {

        loginResponse.observe(this, Observer {
            progress_bar.hide()
            toast(it)
        })
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        toast(message)
    }
}
