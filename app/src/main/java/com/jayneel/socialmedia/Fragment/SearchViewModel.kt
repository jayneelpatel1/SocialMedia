package com.jayneel.socialmedia.Fragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jayneel.socialmedia.Model.userModel

class SearchViewModel : ViewModel() {
    var data = MutableLiveData<ArrayList<userModel>>()
    fun getdata(input:String): MutableLiveData<ArrayList<userModel>>? {

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("user").orderByChild("name").startAt(input).endAt(input+"\uf8ff")

        val userprofile=object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated
                var v=ArrayList<userModel>()

                for (d in dataSnapshot.children){
                   val value = d.getValue(userModel::class.java)!!
                  v.add(value)
                  data.value=v
                  Log.d("TAG", "Value is: $value")
              }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        }


        myRef.addListenerForSingleValueEvent(userprofile)
        return data

    }}