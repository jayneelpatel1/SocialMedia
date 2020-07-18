package com.jayneel.socialmedia

import android.widget.Toast
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jayneel.socialmedia.Model.chatlistModel
import com.jayneel.socialmedia.Model.userModel

class mesglistViewModel:ViewModel()
{
    var data = MutableLiveData<ArrayList<userModel>>()
    var firebaseuser= FirebaseAuth.getInstance().currentUser!!.uid
    var chatlist=MutableLiveData<ArrayList<String>>()
    var rv=MutableLiveData<ArrayList<userModel>>()

     fun getusets(): MutableLiveData<ArrayList<userModel>> {
        var users=ArrayList<userModel>()

        var ref= FirebaseDatabase.getInstance().getReference("user")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (key in snapshot.children)
                {
                    var v=key.getValue(userModel::class.java)
                    if (v != null) {
                        users.add(v)
                    }
                }
                data.value=users
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        return data
    }

     fun getchatlist(): MutableLiveData<ArrayList<String>> {
        var charrlist=ArrayList<String>()
        var myref=FirebaseDatabase.getInstance().getReference("msglist").child(firebaseuser)
        myref.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                charrlist.clear()
                for (key in snapshot.children){
                    var v=key.getValue(String::class.java)
                    if (v != null) {
                        charrlist.add(v)
                    }
                }
                chatlist.value=charrlist
            }


        })
        return chatlist
    }
//    fun rvdata(): MutableLiveData<ArrayList<userModel>> {
//
//       var chat= getchatlist()
//       var user=getusets()
//                var adarlist=ArrayList<userModel>()
//        if (chat != null) {
//            for(k in chat.value!!.iterator()){
//                if (user != null) {
//                    for (u in user.value!!.iterator()){
//                        if (k.id.equals(u.uid))
//                            adarlist.add(u)
//                    }
//                }
//            }
//        }
//                rv.value=adarlist
//        return rv
//    }


}


