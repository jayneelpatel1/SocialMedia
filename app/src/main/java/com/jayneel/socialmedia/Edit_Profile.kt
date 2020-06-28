package com.jayneel.socialmedia

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.jayneel.socialmedia.Fragment.ProfileFragment
import kotlinx.android.synthetic.main.activity_edit__profile.*

class Edit_Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit__profile)
         val viewModel=ViewModelProviders.of(this).get(Edit_ProfileViewModel::class.java)
        val user=FirebaseAuth.getInstance().currentUser!!.uid
        viewModel.getdata(user)?.observe(this, Observer {
            edit_name.setText(it.name)
            edit_username.setText(it.username)
            edit_website.setText(it.email)
        })
        btndone.setOnClickListener {
          viewModel.savedata(user,edit_name.text.toString(),edit_bio.text.toString(),edit_username.text.toString())
        }
        btnlogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this,SignUp::class.java))
            finish()
        }
    }
}