package com.jayneel.socialmedia

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jayneel.socialmedia.Model.userModel

class Add_PostViewModel:ViewModel() {
    val database = FirebaseDatabase.getInstance()
    var myRef = database.getReference("Post")
    var data = MutableLiveData<userModel>()

    fun savedata(uid: String,dis:String,img:String,postid:String,username:String){

        val Post=HashMap<String,Any>()
        Post["uid"]=uid
        Post["disc"]=dis
        Post["username"]=username
        Post["img"]=img
        Post["postid"]=postid
        myRef.child(postid).setValue(Post)
    }
    fun getdata(uid: String): MutableLiveData<userModel>? {
        var myRef= database.getReference("user")
        val userprofile=object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(userModel::class.java)!!
                data.value=value
                Log.d("TAG", "Value is: $value")
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        }
        myRef.child(uid).addListenerForSingleValueEvent(userprofile)
        return data
    }

}