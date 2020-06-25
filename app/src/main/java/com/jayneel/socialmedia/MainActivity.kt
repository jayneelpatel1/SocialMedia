package com.jayneel.socialmedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jayneel.socialmedia.Fragment.AddPoastFragment
import com.jayneel.socialmedia.Fragment.HomeFragment
import com.jayneel.socialmedia.Fragment.ProfileFragment
import com.jayneel.socialmedia.Fragment.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    internal var selectedfragment:Fragment?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)


        if(selectedfragment==null){
            supportFragmentManager.beginTransaction().replace(R.id.fragnent_container,HomeFragment()).commit()
        }

    }
    private val onNavigationItemSelectedListener=BottomNavigationView.OnNavigationItemSelectedListener {
        when(it.itemId) {

            R.id.action_home -> {
                selectedfragment = HomeFragment()
                //Toast.makeText(this,"home",Toast.LENGTH_LONG).show()
                true
            }
            R.id.action_profile -> {
                selectedfragment = ProfileFragment()
                // Toast.makeText(this,"home",Toast.LENGTH_LONG).show()
                true
            }
            R.id.action_addpost -> {
                selectedfragment = AddPoastFragment()
                true
            }
            R.id.action_search -> {
                selectedfragment = SearchFragment()
                true
            }
            R.id.action_notification -> {
                selectedfragment = SearchFragment()
                true
            }

        }
        if(selectedfragment!=null){
            supportFragmentManager.beginTransaction().replace(R.id.fragnent_container,selectedfragment!!).commit()
        }
        false

    }

}