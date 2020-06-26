package com.jayneel.socialmedia

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.profile_fragment.*

class SignUp : AppCompatActivity() {
    override fun onStart() {
        super.onStart()
        var user=FirebaseAuth.getInstance().currentUser
        if(user!=null)
             updateUi(user)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        button5.setOnClickListener {
            startActivity(Intent(this,login::class.java))
            finish()
        }
        //signup
        button4.setOnClickListener {
            var progressDialog= ProgressDialog(this)
            progressDialog.setTitle("It will take some time")
            progressDialog.setMessage("hello")
            progressDialog.show()
            var email=signup_email.text.toString()
            var passowed=signup_password.text.toString()
            var username=signup_userrname.text.toString()
            var name=signup_fullname.text.toString()
            when
            {
                TextUtils.isEmpty(email)->Toast.makeText(this,"Enter Email",Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(passowed)->Toast.makeText(this,"Enter password",Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(username)->Toast.makeText(this,"Enter username",Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(name)->Toast.makeText(this,"Enter full name",Toast.LENGTH_LONG).show()
                else->{
                    var mauth=FirebaseAuth.getInstance()
                    mauth.createUserWithEmailAndPassword(email,passowed).addOnCompleteListener {
                        if (it.isSuccessful){
                            var user=mauth.currentUser
                            var hashMap=HashMap<String,Any>()
                            hashMap["username"]=username
                            hashMap["email"]=email
                            hashMap["uid"]=user!!.uid
                            hashMap["name"]=name

                            var myref=FirebaseDatabase.getInstance().getReference("user")
                            myref.child(user!!.uid.toString()).setValue(hashMap).addOnCompleteListener {
                                Toast.makeText(this,"user created",Toast.LENGTH_SHORT).show()
                                updateUi(user)
                                progressDialog.dismiss()
                            }

                        }
                    }
                        .addOnCanceledListener {
                            Toast.makeText(this,"error try after some time",Toast.LENGTH_LONG).show()
                            progressDialog.dismiss()
                        }
                }
            }

       }
    }

    private fun updateUi(user: FirebaseUser?) {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}