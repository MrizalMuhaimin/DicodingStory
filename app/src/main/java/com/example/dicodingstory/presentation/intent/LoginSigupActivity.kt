package com.example.dicodingstory.presentation.intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.dicodingstory.R
import com.example.dicodingstory.databinding.ActivityLoginSigupBinding
import com.example.dicodingstory.presentation.ui.LoginFragment
import com.example.dicodingstory.presentation.viewmodel.LoginAccountViewModel

class LoginSigupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginSigupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginSigupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val loginViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            LoginAccountViewModel::class.java)

        val mFragmenManager = supportFragmentManager
        val mFragmentLogin= LoginFragment()

        val fragment = mFragmenManager.findFragmentByTag(mFragmentLogin::class.java.simpleName)
        if(fragment !is LoginFragment){
            mFragmenManager
                .beginTransaction()
                .replace(R.id.fragment_container,mFragmentLogin, LoginFragment::class.java.simpleName)
                .commit()
        }


        loginViewModel.isLoading.observe(this,{
            showLoading(it)
        })


    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}