package com.jayneel.socialmedia.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.jayneel.socialmedia.Model.PoastData
import com.jayneel.socialmedia.Model.userModel
import com.jayneel.socialmedia.R
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.posts_rec.view.*
import kotlinx.android.synthetic.main.user_iteam_layout.view.*

class postAdapter(var ctx: Context, var list: ArrayList<PoastData>, var isFragment:Boolean=false): RecyclerView.Adapter<postAdapter.viewholder>() {

    inner class viewholder(v: View): RecyclerView.ViewHolder(v){
        var usernmae=v.post_username
        var img=v.profilr_poast_img
        var caption=v.post_caption
        var profileimg=v.post_profile_img

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        var view=LayoutInflater.from(ctx).inflate(R.layout.posts_rec,parent,false)
        return viewholder(view)
    }
    override fun getItemCount(): Int {
        return list.size
    }



    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.usernmae.setText(list[position].username.toString())
        if(list[position].img!=null){
            val storage = FirebaseStorage.getInstance()
            val storageReference = storage.getReferenceFromUrl(list[position].img!!)
            storageReference.downloadUrl.addOnSuccessListener {
                Glide.with(ctx).load(it.toString()).into(holder.img).view
            }
        }
        holder.caption.setText(list[position].disc.toString())
        postuserinfo(holder.profileimg,holder.usernmae,list[position].uid!!)
    }

    private fun postuserinfo(profileimg: CircleImageView?, usernmae: TextView?,uid:String) {
        var myRef=FirebaseDatabase.getInstance().getReference("user")
        myRef.child(uid).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val value=snapshot.getValue(userModel::class.java)
                if (profileimg != null) {
                    val storage = FirebaseStorage.getInstance()
                    val storageReference = value?.img?.let { storage.getReferenceFromUrl(it) }
                    storageReference!!.downloadUrl.addOnSuccessListener {
                        Glide.with(ctx).load(it.toString()).into(profileimg).view
                    }
                }
                usernmae!!.setText(value!!.username)
            }

        })
    }

}