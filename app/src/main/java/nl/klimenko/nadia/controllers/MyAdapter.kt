package nl.klimenko.nadia.controllers

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nl.klimenko.nadia.R
import nl.klimenko.nadia.models.Article

class MyAdapter(var c: Context, private val items: List<Article>) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(c)
        val itemView = inflater.inflate(R.layout.articles_view, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position])

    }
}