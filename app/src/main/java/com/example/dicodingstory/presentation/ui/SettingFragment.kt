package com.example.dicodingstory.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dicodingstory.R
import com.example.dicodingstory.data.UserPreference
import com.example.dicodingstory.databinding.FragmentSettingBinding
import com.example.dicodingstory.presentation.intent.DicodingStoryActivity
import com.example.dicodingstory.presentation.intent.LoginSigupActivity


class SettingFragment : Fragment() {

    private var _viewBinding: FragmentSettingBinding ? =null
    private val viewBinding get() = _viewBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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

        viewBinding.settingBtnBack.setOnClickListener {
            val intentToStory = Intent(requireActivity(),DicodingStoryActivity::class.java)
            startActivity(intentToStory)
            activity?.finish()
        }

        viewBinding.btnLogout.setOnClickListener{
            val userPreference = UserPreference(context)
            userPreference.setUser(null)
            val intentToLogin = Intent(requireActivity(),LoginSigupActivity::class.java)
            startActivity(intentToLogin)
            activity?.finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }


}