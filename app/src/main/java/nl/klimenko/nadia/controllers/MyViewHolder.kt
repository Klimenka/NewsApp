package nl.klimenko.nadia.controllers

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kotlinx.android.synthetic.main.articles_view.view.*
import nl.klimenko.nadia.models.Article

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind (item: Article) {
        itemView.Title.text = item.Title
        itemView.Image.load(item.Image)

    }
}