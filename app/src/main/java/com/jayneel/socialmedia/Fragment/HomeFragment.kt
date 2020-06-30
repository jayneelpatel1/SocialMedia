package com.jayneel.socialmedia.Fragment

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jayneel.socialmedia.Adapter.postAdapter
import com.jayneel.socialmedia.Adapter.user_Apater
import com.jayneel.socialmedia.Model.PoastData
import com.jayneel.socialmedia.Model.userModel
import com.jayneel.socialmedia.R
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.posts_rec.*
import kotlinx.android.synthetic.main.search_fragment.*

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
        var user=ArrayList<PoastData>()
        user.add(PoastData("aman","",""))
        user.add(PoastData("jayneel","",""))
        user.add(PoastData("hetvi","",""))
        user.add(PoastData("janvi","",""))
        user.add(PoastData("harsh","",""))
        var ad =  postAdapter(context!!, user)
        rvpost.adapter = ad
        rvpost.layoutManager =LinearLayoutManager(context!!.applicationContext, RecyclerView.VERTICAL, false)
    }

}