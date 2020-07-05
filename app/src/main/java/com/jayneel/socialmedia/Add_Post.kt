package com.jayneel.socialmedia

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.jayneel.socialmedia.Model.userModel
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_add__post.*
import kotlinx.android.synthetic.main.activity_edit__profile.*
import kotlinx.android.synthetic.main.posts_rec.*
import java.sql.Time
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class Add_Post : AppCompatActivity() {
    var imaguri:Uri?=null
    var username:String?=null
   var firebaseuid= FirebaseAuth.getInstance().currentUser!!.uid
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add__post)
        val viewModel= ViewModelProviders.of(this).get(Add_PostViewModel::class.java)
        viewModel.getdata(firebaseuid)?.observe(this, androidx.lifecycle.Observer {
          username=it.username
        })
        upload_img_add_post.setOnClickListener {
            CropImage.activity().setAspectRatio(1,1).start(this)
        }
        btn_sub_post.setOnClickListener {
            var progressDialog= ProgressDialog(this)
            progressDialog.setTitle("Uploading Post")
            progressDialog.setMessage("Your Post will uploade in few seconds")
            progressDialog.show()
            var postStorage=FirebaseStorage.getInstance().reference.child("post")
            if(imaguri!=null){
                val fileref = postStorage!!.child(UUID.randomUUID().toString())
                val uploadTask = fileref.putFile(imaguri!!)
                if(uploadTask!=null){
                    uploadTask.addOnCompleteListener {
                        if(it.isSuccessful){
                            var downloadurl=fileref
                            var firebase=FirebaseDatabase.getInstance().getReference("Post")
                            var key=firebase.push().key
                            viewModel.savedata(firebaseuid,post_captionj.text.toString(),downloadurl.toString(),key.toString(),username!!)
                            progressDialog.dismiss()
                            startActivity(Intent(this,MainActivity::class.java))

                        }
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode== Activity.RESULT_OK && data!=null){
            var result=CropImage.getActivityResult(data)
            imaguri=result.uri
            //     var bitmap= MediaStore.Images.Media.getBitmap(contentResolver,data.data)
            upload_img_add_post.setImageURI(imaguri)
        }
    }
}