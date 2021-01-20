package com.example.newsapk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdapter(val newsItemClicked: NewsItemClicked) :
    RecyclerView.Adapter<NewsViewHolder>() {

    private val items = ArrayList<News>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener {
            newsItemClicked.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = items[position]
        holder.titleView.text = currentItem.title
        holder.autherView.text = currentItem.auther
        Glide.with(holder.imgView.context).load(currentItem.imageToURL).into(holder.imgView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateNews(updatedNews: ArrayList<News>) {
        items.clear()
        items.addAll(updatedNews)
        notifyDataSetChanged()
    }

}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView = itemView.findViewById<TextView>(R.id.title)
    val autherView: TextView = itemView.findViewById(R.id.auther)
    val imgView: ImageView = itemView.findViewById(R.id.imgView)

}

interface NewsItemClicked {
    fun onItemClicked(item: News)
}