package com.example.dicodingstory.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.fragment.app.Fragment
import com.example.dicodingstory.data.UserPreference
import com.example.dicodingstory.databinding.FragmentSettingBinding
import com.example.dicodingstory.presentation.intent.LoginSigupActivity


class SettingFragment : Fragment() {

    private var _viewBinding: FragmentSettingBinding ? =null
    private val viewBinding get() = _viewBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _viewBinding = FragmentSettingBinding.inflate(inflater,container,false)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = view.context

        setupAction()

        viewBinding.settingBtnBack.setOnClickListener {
            activity?.finish()
        }

        viewBinding.btnLogout.setOnClickListener{
            val userPreference = UserPreference(context)
            userPreference.setUser(null)
            val intentToLogin = Intent(requireActivity(),LoginSigupActivity::class.java)
            intentToLogin.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intentToLogin)
            activity?.finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    private fun setupAction() {
        viewBinding.btnLaguageSetting.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }




}