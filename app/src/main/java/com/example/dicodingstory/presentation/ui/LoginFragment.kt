package com.example.dicodingstory.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.dicodingstory.R
import com.example.dicodingstory.databinding.FragmentLoginBinding
import kotlin.concurrent.fixedRateTimer


class LoginFragment : Fragment() {

    private var _viewBinding:FragmentLoginBinding ? = null
    private val viewBinding get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _viewBinding = FragmentLoginBinding.inflate(inflater,container,false)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragment = SingUpFragment()

        viewBinding.tvSingUp.setOnClickListener{
            val transaction = fragmentManager?.beginTransaction()
            transaction?.setCustomAnimations(R.anim.slide_in,R.anim.slide_out_invers,R.anim.fade_in,R.anim.fade_out)
            transaction?.replace(R.id.fragment_container, fragment)
            transaction?.commit()


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

}