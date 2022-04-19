package com.example.dicodingstory.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.dicodingstory.R
import com.example.dicodingstory.data.UserPreference
import com.example.dicodingstory.databinding.ActivityLoginSigupBinding
import com.example.dicodingstory.databinding.FragmentLoginBinding
import com.example.dicodingstory.presentation.intent.DicodingStoryActivity
import com.example.dicodingstory.presentation.viewmodel.CreateAccountViewModel
import com.example.dicodingstory.presentation.viewmodel.LoginAccountViewModel
import kotlin.concurrent.fixedRateTimer


class LoginFragment : Fragment() {

    private var _viewBinding:FragmentLoginBinding ? = null
    private val viewBinding get() = _viewBinding!!
    private lateinit var  FIELD_REQUIRED : String

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

        val userPreference = UserPreference(view.context)

        val loginResult = userPreference.getUser()

        if(loginResult.token!!.isNotEmpty()){
            val intentToStory = Intent(requireActivity(),DicodingStoryActivity::class.java)
            startActivity(intentToStory)
            requireActivity().finish()



        }

        val context = view.context

        FIELD_REQUIRED = resources.getString(R.string.field_required)

        val fragment = SingUpFragment()

        val mainViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(
            LoginAccountViewModel::class.java)

        viewBinding.tvSingUp.setOnClickListener{
            val transaction = fragmentManager?.beginTransaction()
            transaction?.setCustomAnimations(R.anim.slide_in,R.anim.slide_out_invers,R.anim.fade_in,R.anim.fade_out)
            transaction?.replace(R.id.fragment_container, fragment)
            transaction?.commit()


        }
        viewBinding.passText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().isNotEmpty())
                    if(p0.toString().length <6){
                        viewBinding.tvWarning.visibility = View.VISIBLE
                    }else{
                        viewBinding.tvWarning.visibility = View.INVISIBLE
                    }

                else
                    viewBinding.tvWarning.visibility = View.INVISIBLE
            }
            override fun afterTextChanged(p0: Editable?) {

            }
        })

        viewBinding.emailText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().isNotEmpty())
                    if(!Patterns.EMAIL_ADDRESS.matcher(p0.toString()).matches()){
                        viewBinding.emailWarning.visibility = View.VISIBLE
                    }else{
                        viewBinding.emailWarning.visibility = View.INVISIBLE
                    }

                else
                    viewBinding.emailWarning.visibility = View.INVISIBLE
            }
            override fun afterTextChanged(p0: Editable?) {

            }
        })

        viewBinding.cirLoginButton.setOnClickListener {
            val email = viewBinding.emailText.text.toString()
            val pass =viewBinding.passText.text.toString()

            if(email.isEmpty()){
                viewBinding.emailText.error = FIELD_REQUIRED
                return@setOnClickListener
            }

            if(pass.isEmpty()){
                viewBinding.passText.error = FIELD_REQUIRED
                return@setOnClickListener
            }

            if(isValidEmail(email) && isValidPassword(pass)){
                mainViewModel.login(email,pass)


                mainViewModel.responseData.observe(viewLifecycleOwner,{

                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()

                    if(it.error == false){

                        val userPreference = UserPreference(context)
                        userPreference.setUser(it.loginResult)

                        val intentToStory = Intent(requireActivity(),DicodingStoryActivity::class.java)
                        startActivity(intentToStory)
                        requireActivity().finish()
                    }
                })
            }
        }
    }

    private fun isValidEmail(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }



    private fun isValidPassword(pass: CharSequence):Boolean{
        return  pass.length>=6
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

}