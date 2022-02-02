package com.example.loginfb

import android.app.Application
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.loginfb.databinding.FragmentDisplayBinding
import com.example.loginfb.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class DisplayFragment : Fragment() {
    private lateinit var application: Application
    // Access a Cloud Firestore instance from your Activity

    private val db = Firebase.firestore
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
        getUserVM(binding)


        //Setupr
        setup(binding)
        FB_actions(binding)
        binding.buttonRecyclerV.setOnClickListener {
        val intent =  Intent(context,RVActivity::class.java)
        startActivity(intent)
        }
        return binding.root
    }

    private fun getUSerDades(binding: FragmentDisplayBinding) {
        // Create a reference to the cities collection
        //val userRef = db.collection("users").document(binding.textViewUser.text.toString())
        val userRef = db.collection("users").whereEqualTo("mail",binding.textViewUser.text.toString())


            userRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    binding.editTextNom.setText(document.getString("nom"))
                    binding.editTextMobil.setText(document.getString("mobil"))
                    binding.editTextNaixement.setText(document.getString("data_naixement"))
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }



       /* userRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    apply {
                        binding.editTextNom.setText(document.getString("nom"))
                        binding.editTextMobil.setText(document.getString("mobil"))
                        binding.editTextNaixement.setText(document.getString("data_naixement"))
                    }
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }*/
        }

    private fun FB_actions(binding: FragmentDisplayBinding) {
        binding.buttonGuardar.setOnClickListener({
            // Create a new user with a first and last name
            val user = hashMapOf(
                "nom" to binding.editTextNom.text.toString(),
                "mobil" to binding.editTextMobil.text.toString(),
                "data_naixement" to binding.editTextNaixement.text.toString(),
                "mail" to binding.textViewUser.text.toString()
            )
            // Add a new document with a generated ID
            db.collection("users").document(binding.textViewUser.text.toString())
                .set(user)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot successfully written!")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }

        }
        )
    }

    private fun getUserVM(binding: FragmentDisplayBinding) {

        val model =
            ViewModelProvider(
                requireActivity()
            ).get(LoginViewModel::class.java)

        model.user.observe(viewLifecycleOwner, Observer {
            binding.textViewUser.text = it
            getUSerDades(binding)
        })

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