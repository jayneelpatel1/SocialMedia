package com.jayneel.socialmedia

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.FirebaseApiNotAvailableException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlin.math.sign

class login : AppCompatActivity() {
    override fun onStart() {
        super.onStart()
        var user=FirebaseAuth.getInstance().currentUser
        if(user!=null){
        updateUi(user)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        button3.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
            finish()
        }
        //login button
        button2.setOnClickListener {
            var progressDialog=ProgressDialog(this)
            progressDialog.setTitle("It will take some time")
            progressDialog.setMessage("hello")
            progressDialog.show()
            var mauth= FirebaseAuth.getInstance()
            var email=login_email.text.toString()
            var passowed=login_password.text.toString()

            mauth.signInWithEmailAndPassword(email,passowed).addOnCompleteListener {
                if(it.isSuccessful) {
                    var user=mauth.currentUser
                    updateUi(user)
                    progressDialog.dismiss()
                }
            }
                .addOnCanceledListener {
                    progressDialog.dismiss()
                }
        }

    }
    private fun updateUi(user: FirebaseUser?) {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}