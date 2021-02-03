package com.khvedelidze.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        if(mAuth.currentUser !== null){
            startActivity(Intent(this, ChatActivity::class.java ))
            finish()
        }


        register_button_register.setOnClickListener {

            val email = email_edittext_register.text.toString()
            val password = password_edittext_register.text.toString()
            val password2 = password2_edittext_register.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Empty", Toast.LENGTH_LONG).show()
            } else {
                mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener { task ->
                        if (password == password2) {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                            Toast.makeText(this, "Register Successful!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Passworcs doesn't match!", Toast.LENGTH_SHORT).show()
                        }
                    }
            }

        }

        already_have_account_text_view.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }

}
