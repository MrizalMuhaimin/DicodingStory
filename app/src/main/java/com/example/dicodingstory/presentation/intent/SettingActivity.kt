package com.example.dicodingstory.presentation.intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dicodingstory.R
import com.example.dicodingstory.presentation.ui.SettingFragment

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        supportActionBar?.hide()

        val mFragmenManager = supportFragmentManager
        val mFragmentSetting = SettingFragment()

        val fragment = mFragmenManager.findFragmentByTag(SettingFragment::class.java.simpleName)
        if(fragment !is SettingFragment){
            mFragmenManager
                .beginTransaction()
                .add(R.id.fragment_setting_container,mFragmentSetting, SettingFragment::class.java.simpleName)
                .commit()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}