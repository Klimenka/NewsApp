package nl.klimenko.nadia.controllers

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import kotlinx.android.synthetic.main.articles_view.view.*
import nl.klimenko.nadia.R
import nl.klimenko.nadia.models.Article

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind (item: Article) {
        itemView.Title.text = item.Title
        itemView.Image.load(item.Image){
            crossfade(true)
           // placeholder(R.drawable.placeholder)
            transformations(RoundedCornersTransformation(10F))

        }

    }
}