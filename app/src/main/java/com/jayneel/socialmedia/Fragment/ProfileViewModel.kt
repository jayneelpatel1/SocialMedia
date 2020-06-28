package com.jayneel.socialmedia.Fragment

import android.util.Log
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
    fun getdata(uid: String): MutableLiveData<userModel>? {

        val database = FirebaseDatabase.getInstance()
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
}



