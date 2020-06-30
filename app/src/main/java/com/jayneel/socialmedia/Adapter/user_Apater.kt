package com.jayneel.socialmedia.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jayneel.socialmedia.Model.userModel
import com.jayneel.socialmedia.R
import kotlinx.android.synthetic.main.user_iteam_layout.view.*

class user_Apater(var ctx:Context,var list: ArrayList<userModel>,var isFragment:Boolean=false):RecyclerView.Adapter<user_Apater.viewholder>() {
    var FirebaseUser=FirebaseAuth.getInstance().currentUser
    var myRef=FirebaseDatabase.getInstance().getReference("Follow")
    inner class viewholder(v:View):RecyclerView.ViewHolder(v){
        var usernmae=v.user_iteam_text
        var img=v.user_iteam_img
        var btn=v.user_btnFollow
        var email=v.txtemail
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
       var view=LayoutInflater.from(ctx).inflate(R.layout.user_iteam_layout,parent,false)
        return viewholder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        chkfollowingstatus(list[position].uid.toString(),holder.btn)
        holder.usernmae.setText(list[position].username)
        holder.email.setText(list[position].email)
        holder.btn.setOnClickListener {
            chkfollowingstatus(list[position].uid.toString(),holder.btn)

            if(holder.btn.text=="Follow"){
                myRef.child(FirebaseUser!!.uid).child("Following").child(list[position].uid.toString()).setValue(true)
                    .addOnCompleteListener {task->
                        if(task.isSuccessful){
                            myRef.child(list[position].uid.toString()).child("Followers").child(
                                FirebaseUser!!.uid).setValue(true)
                        }
                    }
            }
            else
            {
                myRef.child(FirebaseUser!!.uid).child("Following").child(list[position].uid.toString()).removeValue()
                    .addOnCompleteListener {task->
                        if(task.isSuccessful){
                            myRef.child(list[position].uid.toString()).child("Followers").child(FirebaseUser!!.uid).removeValue()
                        }
                    }
            }
        }
    }

    private fun chkfollowingstatus(uid: String, btn: MaterialButton?) {
        var followingref=myRef.child(FirebaseUser!!.uid).child("Following")
        followingref.addValueEventListener(object :ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.child(uid).exists()){
                    btn!!.text="UnFollow"
                }
                else
                {
                    btn!!.text="Follow"
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }


        })

    }

}