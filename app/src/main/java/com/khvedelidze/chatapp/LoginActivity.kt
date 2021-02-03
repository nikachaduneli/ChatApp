package com.khvedelidze.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import com.google.firebase.auth.FirebaseAuth

class LoginActivity: AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        login_button_login.setOnClickListener {
            val email = email_edittext_login.text.toString()
            val password = password_edittext_login.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Empty", Toast.LENGTH_LONG).show()
            } else {
                mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this, ChatActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        back_to_register_textview.setOnClickListener{
            finish()
        }
    }
}