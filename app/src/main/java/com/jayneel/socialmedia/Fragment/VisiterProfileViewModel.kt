package com.jayneel.socialmedia.Fragment

import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jayneel.socialmedia.Model.PoastData
import com.jayneel.socialmedia.Model.userModel
import com.jayneel.socialmedia.R
import kotlinx.android.synthetic.main.visiter_profile_fragment.view.*

class VisiterProfileViewModel : ViewModel() {
    var data = MutableLiveData<userModel>()
    var follower = MutableLiveData<String>()
    var btnStatus = MutableLiveData<String>()
    var following = MutableLiveData<String>()
    val database = FirebaseDatabase.getInstance()
    var post= MutableLiveData<ArrayList<PoastData>>()


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
    fun getposts(uid: String):MutableLiveData<ArrayList<PoastData>> {
        val myRef = database.getReference("Post")
        myRef.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var arlist=ArrayList<PoastData>()
                for(keys in snapshot.children){
                    var value=keys.getValue(PoastData::class.java)
                    if (value?.uid==uid)
                        arlist.add(value!!)
                }
                post!!.value=arlist
            }

        })

        return post
    }

    fun chkfollowingstatus(uid: String, btn: MaterialButton?) {
        val myRef = database.getReference("Follow")
        var followingref=myRef.child(FirebaseAuth.getInstance().currentUser!!.uid).child("Following")
        followingref.addValueEventListener(object :ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.child(uid).exists()){
                    btn!!.text="UnFollow"
                }
                else
                {
                    btn!!.text="Follow"
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }


        })

    }

}