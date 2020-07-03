package com.jayneel.socialmedia.Fragment

import android.content.Context
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
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.jayneel.socialmedia.R
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
            vister_username.setText(it.username)
            if(it.img!="") {
                val storage = FirebaseStorage.getInstance()
                val storageReference = storage.getReferenceFromUrl(it.img!!)

                storageReference.downloadUrl.addOnSuccessListener {
                    Glide.with(this).load(it.toString()).into(circleImageView2).view
                }
            }
        })
        viewModel.getfollowing(uid!!).observe(viewLifecycleOwner, Observer {
            visitor_following_count.setText(it)
        })
        viewModel.getfollower(uid!!).observe(viewLifecycleOwner, Observer {
            visitor_follower_count.setText(it)
        })
    }


}