package com.satyamthakur.memeverse.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.satyamthakur.memeverse.R
import com.satyamthakur.memeverse.models.Meme
import okio.blackholeSink

class MemeAdapter(var memes: List<Meme>) :
    RecyclerView.Adapter<MemeAdapter.MyViewHolder>() {

    lateinit var context: Context

    inner class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var img = v.findViewById<ImageView>(R.id.meme_image)
        var title = v.findViewById<TextView>(R.id.meme_user_title)
        var username = v.findViewById<TextView>(R.id.meme_user_name)
        var totalUpvotes = v.findViewById<TextView>(R.id.meme_total_upvotes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.meme_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = memes.count()

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context)
            .load(memes[position].url)
            .into(holder.img)

        holder.title.text = memes[position].title
        holder.username.text = memes[position].author
        holder.totalUpvotes.text = memes[position].ups.toString()
    }

}