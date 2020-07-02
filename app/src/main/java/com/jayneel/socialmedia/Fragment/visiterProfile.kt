package com.jayneel.socialmedia.Fragment

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jayneel.socialmedia.R
import kotlinx.android.synthetic.main.visiter_profile_fragment.*

class visiterProfile : Fragment() {

    companion object {
        fun newInstance() = visiterProfile()
    }

    private lateinit var viewModel: VisiterProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.visiter_profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(VisiterProfileViewModel::class.java)
        toolbarprofile.title=context!!.getSharedPreferences("sp",Context.MODE_PRIVATE).getString("username  ","nouserfound").toString()
    }

}