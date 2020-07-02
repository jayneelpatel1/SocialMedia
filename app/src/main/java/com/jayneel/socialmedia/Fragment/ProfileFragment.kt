package com.jayneel.socialmedia.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.jayneel.socialmedia.Edit_Profile
import com.jayneel.socialmedia.R
import com.jayneel.socialmedia.login
import kotlinx.android.synthetic.main.activity_edit__profile.*
import kotlinx.android.synthetic.main.profile_fragment.*


class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    var user=FirebaseAuth.getInstance().currentUser?.uid
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        var sp=context?.getSharedPreferences("sp",Context.MODE_PRIVATE)
        sp?.getString("profileid",user)




        btneditprofilr.setOnClickListener {
            startActivity(Intent(context,Edit_Profile::class.java))
        }
        nav.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.action_logout->{
                    var mauth=FirebaseAuth.getInstance()
                    mauth.signOut()
                    startActivity(Intent(context,login::class.java))

                    true
                }
                else->true
            }
        }

viewModel.getfollower(user!!)?.observe(viewLifecycleOwner, Observer {
    profile_follower_count.setText(it)
})
        viewModel.getfollowing(user!!)?.observe(viewLifecycleOwner, Observer {
            profile_following_count.setText(it)
        })
        viewModel.getdata(user!!)?.observe(viewLifecycleOwner, Observer {
            progressBar2.visibility=View.VISIBLE
            profile_full_name.setText(it.name)
            toolbarprofile.title=(it.username)
            if(it.img!="") {
                val storage = FirebaseStorage.getInstance()
                val storageReference = storage.getReferenceFromUrl(it.img!!)

                storageReference.downloadUrl.addOnSuccessListener {
                    Glide.with(this).load(it.toString()).into(circleImageView).view
                }
            }

            progressBar2.visibility=View.INVISIBLE

        })
    }

    override fun onStop() {
        super.onStop()
        var sp=context?.getSharedPreferences("sp",Context.MODE_PRIVATE)?.edit()
        sp?.putString("profileid",user)
        sp?.apply()
        sp?.commit()

    }

    override fun onPause() {
        super.onPause()
        var sp=context?.getSharedPreferences("sp",Context.MODE_PRIVATE)!!.edit()
        sp.putString("profileid",user)
        sp.apply()
        sp.commit()

    }

    override fun onDestroy() {
        super.onDestroy()
        var sp=context?.getSharedPreferences("sp",Context.MODE_PRIVATE)!!.edit()
        sp.putString("profileid",user)
        sp.apply()
        sp.commit()

    }
    }

