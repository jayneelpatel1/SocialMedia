package com.jayneel.socialmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.jayneel.socialmedia.Model.MsgModel
import kotlinx.android.synthetic.main.activity_message.*

class message : AppCompatActivity() {
    var firebaseUser=FirebaseAuth.getInstance().currentUser!!.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        var uid=intent.getStringExtra("uid")

        btnsend.setOnClickListener {
            val msg=edtmsgtext.text.toString()
            if (msg=="")
            {

            }
            else
            {
                sendmsgtouser(uid,firebaseUser,msg)
            }
            edtmsgtext.text.clear()
        }
    }

    private fun sendmsgtouser(reciver: String?, sender: String, msg: String) {
        var myref=FirebaseDatabase.getInstance().getReference("message")
        var msgid=myref.push().key
        var data=MsgModel(msgid,sender,reciver,false,msg)
        if (msgid != null)
        {
            myref.child(msgid).setValue(data).addOnCompleteListener {

            }
        }
    }

}