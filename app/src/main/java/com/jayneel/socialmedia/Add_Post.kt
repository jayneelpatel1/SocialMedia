package com.jayneel.socialmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_add__post.*

class Add_Post : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add__post)
        upload_img_add_post.setOnClickListener {
            CropImage.activity().setAspectRatio(1,1).start(this)
        }
    }
}