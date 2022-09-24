package com.dietTracker.login.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.team.hackathon.login.util.LoginViewModel
import com.team.hackathon.databinding.FragmentLoginPhoneNumberBinding
import com.team.hackathon.login.util.isValidPhoneNumber
import com.google.firebase.auth.FirebaseAuth
import com.team.hackathon.LoginActivity
import java.util.regex.Pattern


class FragmentLoginPhoneNumber : Fragment() {


    private val binding by lazy { FragmentLoginPhoneNumberBinding.inflate(layoutInflater) }
    private val viewModel: LoginViewModel by activityViewModels()
    private val auth = FirebaseAuth.getInstance()

    companion object {
        fun getInstance() = FragmentLoginPhoneNumber()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.phoneNumberRegistration.observe(viewLifecycleOwner) {
            binding.phoneNumberBox.setText(it)
        }

        binding.countryCodePicker.registerCarrierNumberEditText(binding.phoneNumberBox)
        val ccp = binding.countryCodePicker
        ccp.registerCarrierNumberEditText(binding.phoneNumberBox)

        binding.btnToSendOtp.setOnClickListener {
            onSendOTPClicked()
        }

    }

    private fun onSendOTPClicked(){
        val ccp = binding.countryCodePicker
        if (isValidPhoneNumber(ccp.fullNumber)) {
            // for registration number
            viewModel.setPhoneNumberRegistration(binding.phoneNumberBox.text.toString())
            viewModel.setLoginState(LoginActivity.LOGIN_STATE_REGISTER_USER)
            viewModel.setphoneNumber(binding.countryCodePicker.fullNumberWithPlus)
            hideKeyboard()
        } else {
            Toast.makeText(context, "Invalid Number", Toast.LENGTH_SHORT).show()
        }
    }

    private fun hideKeyboard() {
        val inputManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }




}


