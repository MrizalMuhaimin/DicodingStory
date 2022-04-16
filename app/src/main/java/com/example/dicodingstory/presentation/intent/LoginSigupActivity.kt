package com.example.dicodingstory.presentation.intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dicodingstory.R
import com.example.dicodingstory.databinding.ActivityLoginSigupBinding
import com.example.dicodingstory.presentation.ui.LoginFragment

class LoginSigupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_sigup)

        supportActionBar?.hide()

        val mFragmenManager = supportFragmentManager
        val mFragmentLogin= LoginFragment()

        val fragment = mFragmenManager.findFragmentByTag(mFragmentLogin::class.java.simpleName)
        if(fragment !is LoginFragment){
            mFragmenManager
                .beginTransaction()
                .add(R.id.fragment_container,mFragmentLogin, LoginFragment::class.java.simpleName)
                .commit()
        }


    }
}