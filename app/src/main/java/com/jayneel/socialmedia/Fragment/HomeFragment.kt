package com.jayneel.socialmedia.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jayneel.socialmedia.Adapter.postAdapter
import com.jayneel.socialmedia.R
import com.jayneel.socialmedia.message
import com.jayneel.socialmedia.messagelist
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var ad: postAdapter
    private lateinit var viewModel: HomeViewModel
    var postIteamcount:Int=0
    var lastvisible:Int=0
    private var mIsLoading = false
    private val mPostsPerPage = 3
    lateinit var layoutManager:LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        viewModel.getpost(refreslayout,null).observe(viewLifecycleOwner, Observer {
            ad=postAdapter(context!!,it)
            rvpost.adapter = ad
            layoutManager=LinearLayoutManager(context!!.applicationContext, RecyclerView.VERTICAL, false)
            rvpost.layoutManager=layoutManager
        })
        btndone.setOnClickListener {
            var int1=Intent(context, messagelist::class.java)
            startActivity(int1)
        }
        rvpost.addOnScrollListener(object :RecyclerView.OnScrollListener(){


            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
//               postIteamcount=layoutManager.itemCount
//               lastvisible=layoutManager.findLastVisibleItemPosition()
//                var total=ad.itemCount
//                if(!mIsLoading && (postIteamcount+lastvisible)>=total){
//                    ad.getlastIteamId()?.let { viewModel.getpost(null, it) }
//                    mIsLoading=true
//                }

            }
        })
        refreslayout.setOnRefreshListener {

            viewModel.getpost(refreslayout,null)

        }
        }


}

