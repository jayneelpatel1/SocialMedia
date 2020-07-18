package com.jayneel.socialmedia.Adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jayneel.socialmedia.Model.chatlistModel
import com.jayneel.socialmedia.Model.userModel
import com.jayneel.socialmedia.R
import kotlinx.android.synthetic.main.msglist.view.*

class chatlistAdapter(var ctx:Activity,var arlist:ArrayList<userModel>):RecyclerView.Adapter<chatlistAdapter.viewholder>()
{
    inner class viewholder(v:View):RecyclerView.ViewHolder(v)
    {
        var pic=v.chatlistimg
        var usernmae=v.chatlistusername
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        var v=ctx.layoutInflater.inflate(R.layout.msglist,parent,false)
       return viewholder(v)
    }

    override fun getItemCount(): Int {
        return  arlist.size
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.usernmae.text=arlist[position].username.toString()
    }
}