package com.jayneel.socialmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jayneel.socialmedia.Adapter.msgAdapter
import com.jayneel.socialmedia.Model.MsgModel
import kotlinx.android.synthetic.main.activity_message.*

class message : AppCompatActivity() {
    var firebaseUser=FirebaseAuth.getInstance().currentUser!!.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        var uid=intent.getStringExtra("uid")

        retrivemsg(firebaseUser,uid)
        msgrv.setHasFixedSize(true)
        var layoutManager=LinearLayoutManager(applicationContext)
        layoutManager.stackFromEnd=true
        msgrv.layoutManager=layoutManager

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

    private fun retrivemsg(sender: String, reciver: String?) {
        var myref=FirebaseDatabase.getInstance().getReference("message")
        myref.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var arlist=ArrayList<MsgModel>()
                var ad=msgAdapter(this@message,arlist)
                for(key in snapshot.children){
                    var value=key.getValue(MsgModel::class.java)
                    if (value != null) {
                        if(value.sender.equals(sender) && value.reciver.equals(reciver)
                            || value.sender.equals(reciver) && value.reciver.equals(sender))
                            {
                            arlist.add(value)

                        }
                    }

                }
                msgrv.adapter=ad


            }

        })
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