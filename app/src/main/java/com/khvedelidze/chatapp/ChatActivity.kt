package com.khvedelidze.chatapp

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.firestore.auth.User
import kotlinx.android.synthetic.main.activity_chatactivity.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.wrapContent
import com.khvedelidze.chatapp.Message
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.GroupAdapter as GroupAdapter

class ChatActivity: AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    val adapter = GroupieAdapter()
    private lateinit var userNAME:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatactivity)

        mainActivityRecyclerView.adapter = adapter
        mAuth = FirebaseAuth.getInstance()
        logout()

        initFirebase()

        setupSendButton()

        createFirebaseListener()

        getUsername()



    }

    private fun logout() {
        LogOut_Button.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            mAuth.signOut()
            finish()
        }
    }

    private fun getUsername() {
        if(mAuth.currentUser?.uid != null){
            val ref = FirebaseDatabase.getInstance().getReference("/users")

            ref.child(mAuth.currentUser?.uid!!).addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val username = snapshot.getValue(Username::class.java)

                    if (username != null){
                        userNAME = username.username
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })


        }
    }

    private fun createFirebaseListener() {

        val database = FirebaseDatabase.getInstance().getReference("/messages")

        database.addChildEventListener(object :ChildEventListener{

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue(Message::class.java)

                if(message?.id == mAuth.currentUser?.uid!! ){
                    if (message != null) {
                        adapter.add(SentMessages(message.text, "Me"))
                    }
                }else{
                    if (message != null) {
                        adapter.add(RecivedMessages(message.text, message.person))
                    }
                }



            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    /**
     * Setup firebase
     */
    private fun initFirebase() {
        //init firebase
        FirebaseApp.initializeApp(applicationContext)

        FirebaseDatabase.getInstance().setLogLevel(Logger.Level.DEBUG)

        //get reference to our db
        databaseReference = FirebaseDatabase.getInstance().reference
    }

    /**
     * Set listener for Firebase
     */





/*
    private fun setupAdapter(data: ArrayList<Message>){

        val linearLayoutManager = LinearLayoutManager(this)
        mainActivityRecyclerView.layoutManager = linearLayoutManager
        mainActivityRecyclerView.adapter = MessageAdapter(data) {
        }

        //scroll to bottom
        mainActivityRecyclerView.scrollToPosition(data.size - 1)
    }


 */


    private fun setupSendButton() {
        mainActivitySendButton.setOnClickListener {
            if (!mainActivityEditText.text.toString().isEmpty()){
                sendData()
            }else{
                Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Send data to firebase
     */
    private fun sendData(){


        val dataBase = FirebaseDatabase.getInstance().getReference("/messages").push()
        val text = mainActivityEditText.text

        val message = Message(text.toString(), mAuth.currentUser?.uid!!, userNAME)


            dataBase.setValue(message)
                .addOnCompleteListener{ task->
                    if(task.isSuccessful){
                        mainActivityEditText.text = null
                    }else{
                        Toast.makeText(this, "message didn't send", Toast.LENGTH_SHORT).show()
                    }




        }



/*
        databaseReference?.
        child("messages")?.
        child(java.lang.String.valueOf(System.currentTimeMillis()))?.
        setValue(Message(mainActivityEditText.text.toString(),mAuth.currentUser?.uid!!))

        //clear the text
        mainActivityEditText.setText("")

 */
    }

}