package com.jayneel.socialmedia.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.jayneel.socialmedia.Model.MsgModel
import com.jayneel.socialmedia.R
import kotlinx.android.synthetic.main.msgleft.view.*
import kotlinx.android.synthetic.main.msgright.view.*
import org.w3c.dom.Text

class msgAdapter(var mconttxt:Context,var msg:ArrayList<MsgModel>):RecyclerView.Adapter<msgAdapter.viewholder>()
{
    var firebaseUser=FirebaseAuth.getInstance().currentUser!!.uid
    inner class viewholder(v:View):RecyclerView.ViewHolder(v){
        var msg=v.findViewById<TextView>(R.id.show_txt_msg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): viewholder {
        return if (position==1){
            var view=LayoutInflater.from(mconttxt).inflate(R.layout.msgleft,parent,false)
            viewholder(view)
        }
        else{
            var view=LayoutInflater.from(mconttxt).inflate(R.layout.msgright,parent,false)
            viewholder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
       // return super.getItemViewType(position)
        return if(msg[position].sender.equals(firebaseUser))
        {
            1
        }
        else
            0
    }
    override fun getItemCount(): Int {
      return msg.size
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        var chat=msg[position]
       holder.msg.text=chat.msg
    }
}