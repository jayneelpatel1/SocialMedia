package com.jayneel.socialmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var data=ArrayList<PoastData>()
        data.add(PoastData("ad",4,5))
        data.add(PoastData("ad",4,5))
        data.add(PoastData("ad",4,5))
        data.add(PoastData("ad",4,5))
        var ad=PoastAd(this,data)
        rv.adapter=ad
        rv.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
    }

}