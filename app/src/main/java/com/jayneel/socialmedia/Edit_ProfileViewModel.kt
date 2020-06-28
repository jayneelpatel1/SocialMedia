package com.jayneel.socialmedia

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jayneel.socialmedia.Model.userModel

class Edit_ProfileViewModel:ViewModel() {

    val database = FirebaseDatabase.getInstance()
    var data = MutableLiveData<userModel>()
    val myRef = database.getReference("user")
    fun getdata(uid: String): MutableLiveData<userModel>? {

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
        myRef.child(uid).addValueEventListener(userprofile)
        return data
    }
    fun savedata(uid: String,name:String,bio:String,username:String){
        val user=HashMap<String,Any>()
        user["name"]=name
        user["uid"]=uid
        user["bio"]=bio
        user["username"]=username
        myRef.child(uid).setValue(user)
    }
}