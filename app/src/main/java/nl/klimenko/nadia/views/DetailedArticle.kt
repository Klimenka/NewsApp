package nl.klimenko.nadia.views

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import coil.load
import nl.klimenko.nadia.R
import nl.klimenko.nadia.models.Article
import nl.klimenko.nadia.models.User

class DetailedArticle : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val item = intent.getSerializableExtra("Article") as Article
        setContentView(R.layout.article_detailed)
        findViewById<TextView>(R.id.titleDetailed).text = item.Title
        findViewById<ImageView>(R.id.imageDetailed).load(item.Image)
        findViewById<TextView>(R.id.textDetailed).movementMethod = LinkMovementMethod.getInstance()
        findViewById<TextView>(R.id.textDetailed).text = HtmlCompat.fromHtml(item.Summary.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
        findViewById<TextView>(R.id.linkToTheArticle).text =item.Url.toString()
        if(User.getUser().token!=null){
            if(!item.IsLiked!!){
                findViewById<ImageView>(R.id.heart).visibility = View.VISIBLE
            }
            else{
                findViewById<ImageView>(R.id.heartLike).visibility = View.VISIBLE
            }
        }
        Log.i("Detail", "Open article with title ${item.Title}")
    }
}