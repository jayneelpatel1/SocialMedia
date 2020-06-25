package com.jayneel.socialmedia.Fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jayneel.socialmedia.R

class AddPoastFragment : Fragment() {

    companion object {
        fun newInstance() = AddPoastFragment()
    }

    private lateinit var viewModel: AddPoastViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_poast_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddPoastViewModel::class.java)
        // TODO: Use the ViewModel
    }

}