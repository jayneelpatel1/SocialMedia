package com.jayneel.socialmedia.Adapter

import android.content.Context
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
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
import com.jayneel.socialmedia.Fragment.visiterProfile
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
        var btnlike=v.likebtn
        var caption=v.post_caption
        var likes=v.nolike

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
        var post=list[position]
        if(list[position].img!=null){
            val storage = FirebaseStorage.getInstance()
            val storageReference = storage.getReferenceFromUrl(list[position].img!!)
            storageReference.downloadUrl.addOnSuccessListener {
                Glide.with(ctx).load(it.toString()).into(holder.img).view
            }
        }
        holder.caption.setText(list[position].disc.toString())
        postuserinfo(holder.profileimg,holder.usernmae,list[position].uid!!)
        holder.usernmae.setOnClickListener {
            var sp=ctx.getSharedPreferences("sp",Context.MODE_PRIVATE).edit()
            sp.putString("profileid",list[position].uid)
            sp.putString("username",list[position].username)
            sp.apply()
            sp.commit()

            (ctx as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.fragnent_container,
                visiterProfile()
            ).commit()
        }
        getlike(holder.btnlike,post.postid)
        holder.btnlike.setOnClickListener {
         //   Toast.makeText(ctx,"hello",Toast.LENGTH_LONG).show()
            if(holder.btnlike.tag.equals("Like")){
                var myRef=FirebaseDatabase.getInstance().getReference("Post")
                myRef.child(list[position].postid!!).child("likes").child(uid).setValue(true)
            }
            else
            {
                var myRef=FirebaseDatabase.getInstance().getReference("Post")
                myRef.child(list[position].postid.toString()).child("likes").child(uid).removeValue()
            }
            getlike(holder.btnlike,post.postid)
            likecount(post.postid,holder.likes)



        }
        likecount(post.postid,holder.likes)
    }

    private fun likecount(postid: String?, likes: TextView?) {
        var myRef=FirebaseDatabase.getInstance().getReference("Post")
        myRef.child(postid.toString()).child("likes").addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                likes!!.setText(snapshot.childrenCount.toString())
            }

        })
    }


    var uid=FirebaseAuth.getInstance().currentUser!!.uid

    private fun getlike(btnlike: ImageButton?, poastid: String?) {
        var myRef=FirebaseDatabase.getInstance().getReference("Post")
        myRef.child(poastid!!).child("likes").addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child(uid).exists()){
                    btnlike!!.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                    btnlike.tag="Liked"
                }
                else{
                    btnlike!!.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
                    btnlike.tag="Like"
                }
            }

        })
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
                        Log.d("at post uer","posd user")
                    }
                }
                usernmae!!.setText(value!!.username)
            }

        })
    }

}