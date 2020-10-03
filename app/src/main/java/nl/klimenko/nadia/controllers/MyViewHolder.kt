package nl.klimenko.nadia.controllers

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import kotlinx.android.synthetic.main.articles_view.view.*
import nl.klimenko.nadia.models.Article

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: Article, listener: ArticleListener?) {
        itemView.Title.text = item.Title
        itemView.Image.load(item.Image) {
            crossfade(true)
            transformations(RoundedCornersTransformation(10F))
        }
        if (item.IsLiked == true) {
            itemView.heartLike.visibility = View.VISIBLE
        }
        itemView.setOnClickListener {
            listener?.onArticleClicked(item)
        }
    }
}