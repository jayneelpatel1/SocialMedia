package com.jayneel.socialmedia.Fragment

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jayneel.socialmedia.Model.userModel
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileViewModel : ViewModel() {

    lateinit var profiledata: MutableLiveData<List<userModel>>
    var data = MutableLiveData<userModel>()
    var follower = MutableLiveData<String>()
    var following = MutableLiveData<String>()
    val database = FirebaseDatabase.getInstance()

    fun getdata(uid: String): MutableLiveData<userModel>? {

        val myRef = database.getReference("user")

        val userprofile=object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated
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
    fun getfollower(uid:String):MutableLiveData<String>{
        val myRef = database.getReference("Follow")
        myRef.child(uid).child("Followers").addValueEventListener(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                   follower.value=snapshot.childrenCount.toString()
                }
            }

        })

        return follower
    }
    fun getfollowing(uid:String):MutableLiveData<String>{
        val myRef = database.getReference("Follow")
        myRef.child(uid).child("Following").addValueEventListener(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                    following.value=snapshot.childrenCount.toString()
                }
            }

        })

        return following
    }
}



