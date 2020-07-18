package com.jayneel.socialmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jayneel.socialmedia.Adapter.chatlistAdapter
import com.jayneel.socialmedia.Model.chatlistModel
import com.jayneel.socialmedia.Model.userModel
import kotlinx.android.synthetic.main.activity_messagelist.*

class messagelist : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messagelist)
        val viewModel= ViewModelProviders.of(this).get(mesglistViewModel::class.java)

      var user=ArrayList<userModel>()
        var chat=ArrayList<String>()
        viewModel.getchatlist().observe(this, Observer {
            chat.addAll(it)
            rvcall(user,chat)
        })
        viewModel.getusets().observe(this, Observer {
            user.addAll(it)
            rvcall(user,chat)
        })







    }

    private fun rvcall(user: ArrayList<userModel>, chat: ArrayList<String>) {
        var rv=ArrayList<userModel>()
        var ad=chatlistAdapter(this,rv)
        for (c in chat){
            for (u in user)
            {
                if (u.uid.equals(c))
                    rv.add(u)

            }
        }
        rvchatlist.adapter=ad
        rvchatlist.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
    }
}