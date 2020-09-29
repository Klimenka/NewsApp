package nl.klimenko.nadia.views

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import coil.load
import kotlinx.android.synthetic.main.article_detailed.view.*
import nl.klimenko.nadia.R
import nl.klimenko.nadia.models.Article

class DetailedArticle : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val item = intent.getSerializableExtra("Article") as Article
        setContentView(R.layout.article_detailed)
        findViewById<TextView>(R.id.titleDetailed).text = item.Title
        findViewById<ImageView>(R.id.imageDetailed).load(item.Image)
        findViewById<TextView>(R.id.textDetailed).movementMethod = LinkMovementMethod.getInstance()
        findViewById<TextView>(R.id.textDetailed).text = HtmlCompat.fromHtml(item.Summary.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
        Log.i("Detail", "Open article with title ${item.Title}")
    }
}