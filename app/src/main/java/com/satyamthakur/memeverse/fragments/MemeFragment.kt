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
import com.satyamthakur.memeverse.databinding.ActivityMainBinding
import com.satyamthakur.memeverse.databinding.FragmentMemeBinding
import com.satyamthakur.memeverse.models.Meme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MemeFragment : Fragment(R.layout.fragment_meme) {

    lateinit var memList: List<Meme>
    lateinit var binding: FragmentMemeBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMemeBinding.bind(view)

        binding.memeRecyclerView.layoutManager = LinearLayoutManager(context)
        // item decorator for even margins of items in recyclerview
        binding.memeRecyclerView.addItemDecoration(
            MarginItemDecoration(24)
        )
        getMemeNow()

    }

        private fun getMemeNow() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.apiService.getMemes()
                if (response.isSuccessful) {
                    val memesres = response.body()
//                    Log.d("RETROFIT_LOG", memesres.toString())
                    Log.d("RETROFIT_LOG", memesres!!.memes.toString())

                    // get back on UI thread to update recyclerView and adapters
                    withContext(Dispatchers.Main) {
                       memList = memesres!!.memes
                        binding.memeProgressBar.visibility = View.GONE
                        binding.memeRecyclerView.adapter = MemeAdapter(memList)
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