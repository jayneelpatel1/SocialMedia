package com.jayneel.socialmedia.Fragment

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jayneel.socialmedia.Adapter.user_Apater
import com.jayneel.socialmedia.Model.userModel
import com.jayneel.socialmedia.R
import kotlinx.android.synthetic.main.search_fragment.*

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.search_fragment, container, false)

    return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

edit_search.setText("")
        if(edit_search.text!=null) {
            edit_search.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s.toString() == "") {

                    } else {
                        rvsearch.visibility = View.VISIBLE
                        viewModel.getdata(s.toString())
                            ?.observe(viewLifecycleOwner, Observer { list ->
                                var ad = context?.let { user_Apater(it, list) }
                                rvsearch.adapter = ad
                                rvsearch.layoutManager =
                                    LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                            })
                    }

                }

            })
        }

    }

}