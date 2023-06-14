package com.satyamthakur.memeverse.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.satyamthakur.memeverse.R
import com.satyamthakur.memeverse.models.Meme

class MemeAdapter(var context: Context, var memes: List<Meme>) :
    RecyclerView.Adapter<MemeAdapter.MyViewHolder>() {

    inner class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var img = v.findViewById<ImageView>(R.id.meme_image)
        var title = v.findViewById<TextView>(R.id.meme_user_title)
        var username = v.findViewById<TextView>(R.id.meme_user_name)
        var totalUpvotes = v.findViewById<TextView>(R.id.meme_total_upvotes)
        var shareButton = v.findViewById<ImageView>(R.id.meme_share_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.meme_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = memes.count()

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        // Setting up circular progress bar for glide placeholder
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 10f
        circularProgressDrawable.centerRadius = 60f
        circularProgressDrawable.start()

        // TODO: Add share button
        holder.shareButton.setOnClickListener {

            val context=holder.shareButton.context
            val sendIntent = Intent(Intent.ACTION_SEND)
            sendIntent.type = "text/plain"
            sendIntent.putExtra(Intent.EXTRA_TEXT, memes[position].url)
            context.startActivity(sendIntent)

        }


        Glide.with(context)
            .load(memes[position].url)
            .placeholder(circularProgressDrawable)
            .into(holder.img)

        holder.title.text = memes[position].title
        holder.username.text = memes[position].author
        holder.totalUpvotes.text = memes[position].ups.toString()
    }


}