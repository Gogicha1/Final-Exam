package com.example.samazon.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.samazon.ChangePasswordActivity
import com.example.samazon.LoginActivity
import com.example.samazon.R
import com.example.samazon.data.UserInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var userNameTextView : TextView
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance().getReference("Users")



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userNameTextView = view.findViewById(R.id.userName)


        db.child(auth.currentUser?.uid!!).get().addOnSuccessListener {
            if (it.exists()) {
                val userName = it.child("userName").value


                userNameTextView.text = "$userName"
            }
        }
    }
}
