package com.example.samazon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextPassword1: EditText
    private lateinit var buttonRegister: Button
    private lateinit var textView1: TextView
    private lateinit var textView2: TextView
    private lateinit var textView3: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        init()
        registerListeners()
    }
    private fun init() {
        editTextEmail = findViewById(R.id.editEmail)
        editTextPassword = findViewById(R.id.editPassword)
        editTextPassword1 = findViewById(R.id.editPass)
        buttonRegister = findViewById(R.id.buttonRegister)
        textView1 = findViewById(R.id.textView1)
        textView2 = findViewById(R.id.textView2)
        textView3 = findViewById(R.id.textView3)
    }
    private fun registerListeners() {

        buttonRegister.setOnClickListener {

            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val password1 = editTextPassword1.text.toString()

            if (emailValidate(email) && passwordValidate(password, password1 )) {
                textView1.text = ""
                textView2.text = ""
                textView3.text = ""

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        }
                    }

            }
        }
    }

    private fun emailValidate(email: String): Boolean {
        return if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textView1.text = ""
            true
        } else {
            textView1.text = "Please Enter an existing E-mail!"
            false
        }
    }

    private fun passwordValidate(password1: String, password2: String): Boolean {

        if (password1.length < 8) {
            textView2.text = "Required At Least 8 Characters Long!"
            return false
        }
        else if (!password1.matches(".*[!@#$%^&*+=/?].*".toRegex())) {
            textView2.text = "Required at least 1 Symbol!"
            return false
        }
        else if (password1 != password2) {
            textView3.text = "Passwords must be identical!"
            return false
        }
        else return true
    }
}