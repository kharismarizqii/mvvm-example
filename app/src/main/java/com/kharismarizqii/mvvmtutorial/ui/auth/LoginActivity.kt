package com.kharismarizqii.mvvmtutorial.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kharismarizqii.mvvmtutorial.R
import com.kharismarizqii.mvvmtutorial.data.db.AppDatabase
import com.kharismarizqii.mvvmtutorial.data.db.entities.User
import com.kharismarizqii.mvvmtutorial.data.network.MyApi
import com.kharismarizqii.mvvmtutorial.data.repository.UserRepository
import com.kharismarizqii.mvvmtutorial.databinding.ActivityLoginBinding
import com.kharismarizqii.mvvmtutorial.ui.home.HomeActivity
import com.kharismarizqii.mvvmtutorial.util.hide
import com.kharismarizqii.mvvmtutorial.util.show
import com.kharismarizqii.mvvmtutorial.util.snackbar
import com.kharismarizqii.mvvmtutorial.util.toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), AuthListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val api = MyApi()
        val db = AppDatabase(this)
        val repository = UserRepository(api,db)
        val factory = AuthViewModelFactory(repository)

        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.authListener = this

        viewModel.getLoggedInUser().observe(this, Observer {user ->
            if (user != null){
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })
    }

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSuccess(user: User) {
        progress_bar.hide()
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }
}
