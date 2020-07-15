package com.jayneel.socialmedia.Fragment

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.jayneel.socialmedia.Adapter.profilePostAdapter
import com.jayneel.socialmedia.R
import com.jayneel.socialmedia.message
import kotlinx.android.synthetic.main.center_profile.*
import kotlinx.android.synthetic.main.follower_top_profile.*
import kotlinx.android.synthetic.main.profile_fragment.*
import kotlinx.android.synthetic.main.visiter_profile_fragment.*

class visiterProfile : Fragment() {

    companion object {
        fun newInstance() = visiterProfile()
    }

    private lateinit var viewModel: VisiterProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.visiter_profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var firebaseUser=FirebaseAuth.getInstance().currentUser!!.uid
        var sp=context!!.getSharedPreferences("sp",Context.MODE_PRIVATE)
        var uid=sp.getString("profileid","no-userFound")
        viewModel = ViewModelProviders.of(this).get(VisiterProfileViewModel::class.java)
        viewModel.getdata(uid!!)!!.observe(viewLifecycleOwner, Observer {
            vister_username.setText(it.username.toString())
            visitor_email.setText(it.email)
            if(it.img!="") {
                val storage = FirebaseStorage.getInstance()
                val storageReference = storage.getReferenceFromUrl(it.img!!)

                storageReference.downloadUrl.addOnSuccessListener {
                    Glide.with(this).load(it.toString()).into(circleImageView2).view
                }
            }
        })
        btnmsg.setOnClickListener {
            var int1=Intent(context,message::class.java)
            int1.putExtra("uid",uid.toString())
            startActivity(int1)
        }
viewModel.getposts(uid).observe(viewLifecycleOwner, Observer {
    it.reverse()
    var ad= profilePostAdapter(context!!,it)
    rvpostvisiter.adapter = ad
    rvpostvisiter.layoutManager=
        GridLayoutManager(context!!.applicationContext,3, RecyclerView.VERTICAL,false)

})


        visiter_btn_follow.setOnClickListener {
            var myRef = FirebaseDatabase.getInstance().getReference("Follow")
            if (visiter_btn_follow.text == "Follow") {
                myRef.child(firebaseUser).child("Following").child(uid.toString()).setValue(true)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            myRef.child(uid.toString()).child("Followers").child(
                                firebaseUser
                            ).setValue(true)
                        }
                    }
            } else {
                myRef.child(firebaseUser).child("Following").child(uid.toString()).removeValue()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            myRef.child(uid.toString()).child("Followers").child(
                                firebaseUser
                            ).removeValue()
                        }
                    }
            }
            viewModel.chkfollowingstatus(uid!!,visiter_btn_follow!!)

        }
        viewModel.getfollowing(uid!!).observe(viewLifecycleOwner, Observer {
            visitor_following_count.setText(it)
        })
        viewModel.getfollower(uid!!).observe(viewLifecycleOwner, Observer {
            visitor_follower_count.setText(it)
        })
        viewModel.chkfollowingstatus(uid!!,visiter_btn_follow!!)
    }


}