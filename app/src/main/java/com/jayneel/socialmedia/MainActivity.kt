package com.jayneel.socialmedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jayneel.socialmedia.Fragment.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        movetoFragment(HomeFragment())

    }
   private fun movetoFragment(fragment:Fragment){
       val fragmrntTrans=supportFragmentManager.beginTransaction()
       fragmrntTrans.replace(R.id.fragnent_container,fragment)
        fragmrntTrans.commit()
    }
    private val onNavigationItemSelectedListener=BottomNavigationView.OnNavigationItemSelectedListener {
        when(it.itemId) {

            R.id.action_home -> {
                movetoFragment(HomeFragment())
                it.setChecked(true)
                //Toast.makeText(this,"home",Toast.LENGTH_LONG).show()
                true
            }
            R.id.action_profile -> {
                movetoFragment(ProfileFragment())
                // Toast.makeText(this,"home",Toast.LENGTH_LONG).show()
                it.setChecked(true)
                true
            }
            R.id.action_addpost -> {
                movetoFragment( AddPoastFragment())
                it.setChecked(true)
                true
            }
            R.id.action_search -> {
                movetoFragment(SearchFragment())
                it.setChecked(true)
                true
            }
            R.id.action_notification -> {
                movetoFragment( NotificationFragment())
                it.setChecked(true)
                true
            }

        }
        false

    }

}