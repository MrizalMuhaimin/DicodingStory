package com.example.dicodingstory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.dicodingstory.presentation.intent.LoginSigupActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        supportActionBar?.hide()
        val timeDelay = 3000

        Handler(Looper.getMainLooper()).postDelayed({
            val intentToLoginSigupActivity = Intent(this@MainActivity, LoginSigupActivity::class.java)
            startActivity(intentToLoginSigupActivity)
            finish()
        },timeDelay.toLong())
    }
}