package com.khvedelidze.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser !== null) {
            startActivity(Intent(this, ChatActivity::class.java))
            finish()
        }


        register_button_register.setOnClickListener {
            val userName = username_edittext_register.text.toString()
            val email = email_edittext_register.text.toString()
            val password = password_edittext_register.text.toString()
            val password2 = password2_edittext_register.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Empty", Toast.LENGTH_LONG).show()
            } else {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (password == password2) {

                                mAuth.signInWithEmailAndPassword(email.toString(), password.toString())
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                val uid = FirebaseAuth.getInstance().uid
                                                val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
                                                val userUsername = Username(userName.toString())
                                                ref.setValue(userUsername)

                                                Toast.makeText(this, "Register Successful!", Toast.LENGTH_SHORT).show()

                                                startActivity(Intent(this, ChatActivity::class.java))
                                                finish()
                                            } else {
                                                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                                            }


                                        }
                            }

                        }
            }


            }

        already_have_account_text_view.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }
}
