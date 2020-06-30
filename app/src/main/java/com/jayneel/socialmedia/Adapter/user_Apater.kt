package com.jayneel.socialmedia.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jayneel.socialmedia.Model.userModel
import com.jayneel.socialmedia.R
import kotlinx.android.synthetic.main.user_iteam_layout.view.*

class user_Apater(var ctx:Context,var list: ArrayList<userModel>,var isFragment:Boolean=false):RecyclerView.Adapter<user_Apater.viewholder>() {
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
        holder.usernmae.setText(list[position].username)
        holder.email.setText(list[position].email)
    }
}