package com.satyamthakur.memeverse.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.satyamthakur.memeverse.R
import com.satyamthakur.memeverse.Utils.MarginItemDecoration
import com.satyamthakur.memeverse.adapters.MemeAdapter
import com.satyamthakur.memeverse.api.RetrofitClient
import com.satyamthakur.memeverse.databinding.FragmentMemeBinding
import com.satyamthakur.memeverse.models.Meme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MemeFragment : Fragment(R.layout.fragment_meme) {

    var memList = mutableListOf<Meme>()
    lateinit var binding: FragmentMemeBinding

    // for infinite scroll
    var isLoading = false

    // Adapter
    lateinit var mAdapter: MemeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMemeBinding.bind(view)

        var layoutManager = LinearLayoutManager(context)
        binding.memeRecyclerView.layoutManager = layoutManager

        mAdapter = MemeAdapter(memList)

        binding.memeRecyclerView.adapter = mAdapter
        // item decorator for even margins of items in recyclerview
        binding.memeRecyclerView.addItemDecoration(
            MarginItemDecoration(24)
        )
        getMemeNow()

        binding.memeRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
                val total = mAdapter.itemCount

                if (!isLoading) {
                    if (visibleItemCount + pastVisibleItem >= total) {
                        getMemeNow()
                    }
                }
            }
        })


    }

    private fun getMemeNow() {
        isLoading = true

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.apiService.getMemes()
                if (response.isSuccessful) {
                    val memesres = response.body()
//                    Log.d("RETROFIT_LOG", memesres.toString())
                    Log.d("RETROFIT_LOG", memesres!!.memes.toString())

                    // get back on UI thread to update recyclerView and adapters
                    withContext(Dispatchers.Main) {
                        memList.addAll(memesres!!.memes as MutableList<Meme>)

                        mAdapter.notifyDataSetChanged()

                        binding.memeProgressBar.visibility = View.GONE
                        isLoading = false
                    }

                } else {
                    Log.e("RETROFIT_LOG", response.code().toString())
                }
            } catch (e: Exception) {
                Log.e("RETROFIT_LOG", e.message.toString())
            }
        }
    }

}