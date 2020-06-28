package com.jayneel.socialmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
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
    }
}