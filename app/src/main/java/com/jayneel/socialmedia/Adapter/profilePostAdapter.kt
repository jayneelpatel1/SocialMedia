package com.jayneel.socialmedia.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.jayneel.socialmedia.Model.PoastData
import com.jayneel.socialmedia.R
import kotlinx.android.synthetic.main.profilepost.view.*

class profilePostAdapter(var ctx: Context, var list: ArrayList<PoastData>, var isFragment:Boolean=false):RecyclerView.Adapter<profilePostAdapter.viewholder>() {
    inner class viewholder(v:View):RecyclerView.ViewHolder(v){
        var img=v.profile_post_img
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        var view= LayoutInflater.from(ctx).inflate(R.layout.profilepost,parent,false)
        return viewholder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        if(list[position].img!="")
        {
            val storage = FirebaseStorage.getInstance()
            val storageReference = storage.getReferenceFromUrl(list[position].img!!)
            storageReference.downloadUrl.addOnSuccessListener {
                Glide.with(ctx).load(it.toString()).into(holder.img).view
            }
        }
    }
}