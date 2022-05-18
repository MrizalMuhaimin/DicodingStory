package com.example.dicodingstory.presentation.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.dicodingstory.R
import com.example.dicodingstory.databinding.FragmentSingUpBinding
import com.example.dicodingstory.presentation.viewmodel.CreateAccountViewModel


class SingUpFragment : Fragment() {

    private var _viewBinding: FragmentSingUpBinding? = null
    private val viewBinding get() = _viewBinding!!
    private lateinit var  FIELD_REQUIRED : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _viewBinding = FragmentSingUpBinding.inflate(inflater,container,false)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragment = LoginFragment()

        FIELD_REQUIRED = resources.getString(R.string.field_required)

        val mainViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(
            CreateAccountViewModel::class.java)

        viewBinding.tvSingUp.setOnClickListener{
            val transaction = fragmentManager?.beginTransaction()
            transaction?.setCustomAnimations(R.anim.slide_in_invers,R.anim.slide_out,R.anim.fade_in,R.anim.fade_out)
            transaction?.replace(R.id.fragment_container, fragment)
            transaction?.commit()

        }


        viewBinding.cirLoginButton.setOnClickListener {
            val name = viewBinding.etNama.text.toString()
            val email = viewBinding.emailText.text.toString()
            val pass =viewBinding.passText.text.toString()

            if(name.isEmpty()){
                viewBinding.etNama.error = FIELD_REQUIRED
                return@setOnClickListener

            }
            if(email.isEmpty()){
                viewBinding.emailText.error = FIELD_REQUIRED
                return@setOnClickListener
            }

            if(pass.isEmpty()){
                viewBinding.passText.error = FIELD_REQUIRED
                return@setOnClickListener
            }

            if(isValidName(name) && isValidEmail(email) && isValidPassword(pass)){

                mainViewModel.register(name,email,pass)


                mainViewModel.responseData.observe(viewLifecycleOwner,{

                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()

                    if(it.error == false){
                        val transaction = fragmentManager?.beginTransaction()
                        transaction?.setCustomAnimations(R.anim.slide_in_invers,R.anim.slide_out,R.anim.fade_in,R.anim.fade_out)
                        transaction?.replace(R.id.fragment_container, fragment)
                        transaction?.commit()
                    }

                })


            }

        }

    }


    private fun isValidName(name:CharSequence):Boolean{
        return name.isNotEmpty()
    }

    private fun isValidEmail(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(pass: CharSequence):Boolean{
        return  pass.length>=6
    }


    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }

}