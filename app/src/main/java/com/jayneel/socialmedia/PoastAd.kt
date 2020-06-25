package com.jayneel.socialmedia

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.posts_rec.view.*

class PoastAd(var ctx: Activity, var arlist:ArrayList<PoastData>): RecyclerView.Adapter<PoastAd.viewholder>(){


    inner class viewholder(v: View):RecyclerView.ViewHolder(v){
        var name=v.textView
        var imgprofile=v.img
        var imgpost=v.imageView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        var view=ctx.layoutInflater.inflate(R.layout.posts_rec,parent,false)
        return viewholder(view)
    }

    override fun getItemCount(): Int {
        return arlist.size
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.imgpost.setImageResource(R.drawable.aman)
        holder.imgprofile.setImageResource(R.drawable.aman)
        holder.name.text="Aman"
    }
}