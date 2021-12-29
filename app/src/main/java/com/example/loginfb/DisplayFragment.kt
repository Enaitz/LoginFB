package com.example.loginfb

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.loginfb.databinding.FragmentDisplayBinding
import com.example.loginfb.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth


class DisplayFragment : Fragment() {
    private lateinit var application : Application
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        application = requireNotNull(this.activity).application
        val binding: FragmentDisplayBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_display, container, false
        )
        binding.setLifecycleOwner(this)
        // Inflate the layout for this fragment

        //Setup
        setup(binding)
        return binding.root
    }

    private fun setup(binding: FragmentDisplayBinding) {
        //HAcer viewmodel para pasar datos de un lado a otro???
        //binding.textViewUser.text
        binding.buttonLogout.setOnClickListener(View.OnClickListener {
            FirebaseAuth.getInstance().signOut()
            it.findNavController().navigate(R.id.action_displayFragment_to_loginFragment)
        })
    }
}