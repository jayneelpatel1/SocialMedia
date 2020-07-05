package com.jayneel.socialmedia.Fragment

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jayneel.socialmedia.Adapter.postAdapter
import com.jayneel.socialmedia.Model.PoastData
import com.jayneel.socialmedia.Model.userModel

class HomeViewModel : ViewModel() {
    var data = MutableLiveData<ArrayList<PoastData>>()
    val database = FirebaseDatabase.getInstance()

    fun getpost():MutableLiveData<ArrayList<PoastData>>{
        val myRef = database.getReference("Post")
        val post=object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated
                var arlis=ArrayList<PoastData>()

                for(snap in dataSnapshot.children){
                    val value = snap.getValue(PoastData::class.java)!!
                   arlis.add(value)
                    Log.d("TAG", "Value is: $value")
                }
                data.value=arlis
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        }
        myRef.addListenerForSingleValueEvent(post)
        return data
    }
}