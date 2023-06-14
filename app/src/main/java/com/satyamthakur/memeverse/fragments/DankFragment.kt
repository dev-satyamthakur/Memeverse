package com.satyamthakur.memeverse.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.satyamthakur.memeverse.R
import com.satyamthakur.memeverse.Utils.MarginItemDecoration
import com.satyamthakur.memeverse.adapters.MemeAdapter
import com.satyamthakur.memeverse.api.RetrofitClient
import com.satyamthakur.memeverse.databinding.FragmentDankBinding
import com.satyamthakur.memeverse.databinding.FragmentMemeBinding
import com.satyamthakur.memeverse.models.Meme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DankFragment : Fragment(R.layout.fragment_dank) {

    private var memList = mutableListOf<Meme>()
    private var _binding: FragmentDankBinding? = null
    private lateinit var binding: FragmentDankBinding

    // for infinite scroll
    private var isLoading = false

    // Adapter
    private lateinit var dankMemeAdapter: MemeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDankBinding.bind(view)

        var layoutManager = LinearLayoutManager(context)
        binding.dankMemeRecyclerView.layoutManager = layoutManager

        dankMemeAdapter = MemeAdapter(requireContext(), memList)

        binding.dankMemeRecyclerView.adapter = dankMemeAdapter
        // item decorator for even margins of items in recyclerview
        binding.dankMemeRecyclerView.addItemDecoration(
            MarginItemDecoration(24)
        )
        getMemeNow()

        binding.dankMemeRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
                val total = dankMemeAdapter.itemCount

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
        binding.dankMemeProgressBar.visibility = View.VISIBLE
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.apiService.getDankMemes()
                if (response.isSuccessful) {
                    val memesres = response.body()
//                    Log.d("RETROFIT_LOG", memesres.toString())
                    Log.d("RETROFIT_LOG", memesres!!.memes.toString())

                    // get back on UI thread to update recyclerView and adapters
                    withContext(Dispatchers.Main) {
                        memList.addAll(memesres!!.memes as MutableList<Meme>)

                        dankMemeAdapter.notifyDataSetChanged()

                        binding.dankMemeProgressBar.visibility = View.GONE
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}