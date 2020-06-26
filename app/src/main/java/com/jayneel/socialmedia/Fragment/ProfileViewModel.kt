package com.jayneel.socialmedia.Fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jayneel.socialmedia.Model.userModel

class ProfileViewModel : ViewModel() {

    lateinit var profiledata:LiveData<List<userModel>>

    fun getdata(uid:String){
        var myref=FirebaseDatabase.getInstance().getReference("user")
        myref.child(uid).addValueEventListener(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
            var data=ArrayList<userModel>()
            override fun onDataChange(snapshot: DataSnapshot) {
              for(ds in snapshot.children)
              {

              }
            }

        })
    }


}


