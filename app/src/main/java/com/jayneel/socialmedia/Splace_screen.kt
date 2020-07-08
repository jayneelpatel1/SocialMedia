package com.jayneel.socialmedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class Splace_screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splace_screen)

        Handler().postDelayed({
            startActivity(Intent(this,login::class.java))
            finish()
        },3000)
    }
    }