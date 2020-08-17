package com.jayneel.socialmedia.Fragment

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.jayneel.socialmedia.Adapter.postAdapter
import com.jayneel.socialmedia.Model.PoastData
import com.jayneel.socialmedia.Model.userModel

class HomeViewModel : ViewModel() {
    var data = MutableLiveData<ArrayList<PoastData>>()
    val database = FirebaseDatabase.getInstance()
    var firebaseuser=FirebaseAuth.getInstance().currentUser
    var following=MutableLiveData<ArrayList<String>>()
    var limit=3
    var lastkey=MutableLiveData<String>()
    fun getpost(ref: SwipeRefreshLayout?,nodeid:String?):MutableLiveData<ArrayList<PoastData>>{
        getlastKey()
        var myRef:Query?=null
        var r=getfollowin()
        //if (nodeid==null){
          //  data.value?.clear()
            myRef=database.getReference("Post").orderByChild("dateTime")

        //}
//        else
//        {
//            myRef=database.getReference("Post").orderByChild("dateTime").startAt(nodeid).limitToFirst(limit)
//        }

        val post=object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated
                if (ref != null) {
                    ref.isRefreshing=true
                }
                var arlis=ArrayList<PoastData>()
                arlis.clear()
                var count=dataSnapshot.childrenCount.toInt()
                for(snap in dataSnapshot.children){
                    val value = snap.getValue(PoastData::class.java)!!
                  if(following.value!=null) {
                      for (follow in following.value!!.iterator()) {
                          if (value.uid.toString().equals(follow)) {
                              arlis.add(value)
                              Log.d("TAG", "Value is: $value")
                          }

                      }
                  }
                    if (value.uid.toString().equals(firebaseuser!!.uid)){
                        arlis.add(value)

                    }
                }
                if (ref != null) {
                    ref.isRefreshing=false
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

   private fun getlastKey(){
        var myRef=database.getReference("Post").orderByChild("dateTime")
        myRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Log.d("fb","key not found")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (key in snapshot.children)
                {
                    lastkey.value=key.key
                }
            }

        })

    }

    private fun getfollowin(): MutableLiveData<ArrayList<String>>? {
        val myRef = database.getReference("Follow")
        myRef.child(firebaseuser!!.uid).child("Following").addValueEventListener(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
               var arlist=ArrayList<String>()
                for(keys in snapshot.children){
                    arlist.add(keys.key.toString())
                }
                following!!.value=arlist
            }

        })

        return following
    }


}

