package com.jayneel.socialmedia

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.jayneel.socialmedia.Fragment.HomeFragment
import com.jayneel.socialmedia.Fragment.ProfileFragment
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_edit__profile.*


class Edit_Profile : AppCompatActivity() {
    private var cheaked=""
    private lateinit var firebaseUser: FirebaseUser
    private var myUrl=""
    private var imaguri:Uri?=null
    private var fireabaseStorage:StorageReference?=null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==Activity.RESULT_OK && data!=null){
            var result=CropImage.getActivityResult(data)
            imaguri=result.uri
       //     var bitmap= MediaStore.Images.Media.getBitmap(contentResolver,data.data)
            edit_image.setImageURI(imaguri)

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit__profile)
         val viewModel=ViewModelProviders.of(this).get(Edit_ProfileViewModel::class.java)
        val user=FirebaseAuth.getInstance().currentUser!!.uid
        var profileimgurl:String?=null
        fireabaseStorage=FirebaseStorage.getInstance().reference.child("Profile_pic")
        viewModel.getdata(user)?.observe(this, Observer {
            edit_name.setText(it.name)
            edit_username.setText(it.username)
          edit_bio.setText(it.bio)
            if(it.img!="") {
                val storage = FirebaseStorage.getInstance()
                val storageReference = storage.getReferenceFromUrl(it.img!!)
                profileimgurl=it.img.toString()
                storageReference.downloadUrl.addOnSuccessListener {
                    Glide.with(this).load(it.toString()).into(edit_image).view
                }
            }
            edit_website.setText(it.email)
        })
        btndone.setOnClickListener {

            progressBar.visibility=View.VISIBLE
            if(imaguri!=null) {
                val fileref = fireabaseStorage!!.child(user + ".jpg")
                val uploadTask = fileref.putFile(imaguri!!)
                if (uploadTask != null) {
                    uploadTask
                        .addOnCompleteListener { taskSnapshot -> // Get a URL to the uploaded content
                            val downloadUrl = fileref
                            progressBar.visibility = View.GONE
                            viewModel.savedata(
                                user,
                                edit_name.text.toString(),
                                edit_bio.text.toString(),
                                edit_username.text.toString(),
                                edit_website.text.toString(),
                                downloadUrl.toString()
                            )
                            progressBar.visibility=View.GONE
                            startActivity(Intent(this,MainActivity::class.java))
                            finish()
                        }
                        .addOnFailureListener {
                            // Handle unsuccessful uploads
                            // ...
                            progressBar.visibility=View.GONE
                        }
                }
                else
                {
                    progressBar.visibility=View.GONE
                }
            }
            else
            {
                viewModel.savedataonly(
                    user,
                    edit_name.text.toString(),
                    edit_bio.text.toString(),
                    edit_username.text.toString(),
                    edit_website.text.toString(),profileimgurl.toString()
                )
                progressBar.visibility=View.GONE
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }



        }
        btnlogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this,login::class.java))
            finish()

        }
        edit_change_profile.setOnClickListener {
            CropImage.activity().setAspectRatio(1,1).start(this)
        }
    }
}