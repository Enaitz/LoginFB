package com.example.loginfb

import android.app.AlertDialog
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController

import com.example.loginfb.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {
    lateinit var application : Application
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        application = requireNotNull(this.activity).application
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false
        )
        binding.setLifecycleOwner(this)
        //Setup
        setup(binding)



        return binding.root
    }

    private fun setup(binding:FragmentLoginBinding ) {
        //title="Authentication"
        binding.buttonRegister.setOnClickListener(View.OnClickListener {v->
            if (binding.editTextPassword.text.isNotEmpty()&&binding.editTextUser.text.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(binding.editTextUser.text.toString(),binding.editTextPassword.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(application,"Usuari enregistrat!",Toast.LENGTH_LONG).show()
                        saveUSerVM(binding.editTextUser.text.toString())
                    }else{
                        showAlert()
                    }
                }

            }
        })
        binding.buttonLogin.setOnClickListener(View.OnClickListener {v->
            if (binding.editTextPassword.text.isNotEmpty()&&binding.editTextUser.text.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(binding.editTextUser.text.toString(),binding.editTextPassword.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful){
                        saveUSerVM(binding.editTextUser.text.toString())
                        v.findNavController().navigate(R.id.action_loginFragment_to_displayFragment)

                    }else{
                        //showAlert()
                        Log.i("No Register","No ah pogut enregistrar")
                    }
                }

            }
        })
    }

    private fun saveUSerVM(user:String) {
        val model =
            ViewModelProvider(
                requireActivity()
            ).get(LoginViewModel::class.java)

        model.setUser(user)
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(activity!!)
        builder.setTitle("error")
        builder.setMessage("S'ha produit un error en l'autenticació")
        builder.setPositiveButton("Acceptar",null)
        val dialog :AlertDialog = builder.create()
        dialog.show()

    }

}