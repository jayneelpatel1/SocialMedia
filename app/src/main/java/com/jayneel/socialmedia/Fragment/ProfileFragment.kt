package com.jayneel.socialmedia.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jayneel.socialmedia.Edit_Profile
import com.jayneel.socialmedia.Model.userModel
import com.jayneel.socialmedia.R
import com.jayneel.socialmedia.login
import kotlinx.android.synthetic.main.profile_fragment.*
import kotlinx.android.synthetic.main.profile_fragment.view.*


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

    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
        var user=FirebaseAuth.getInstance().currentUser?.uid

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


        viewModel.getdata(user!!)?.observe(viewLifecycleOwner, Observer {
            progressBar2.visibility=View.VISIBLE
            profile_full_name.setText(it.name)
            toolbarprofile.title=(it.username)
            progressBar2.visibility=View.INVISIBLE

        })
    }



    }

