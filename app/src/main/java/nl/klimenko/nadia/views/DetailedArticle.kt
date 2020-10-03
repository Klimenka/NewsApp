package nl.klimenko.nadia.views

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import coil.load
import nl.klimenko.nadia.R
import nl.klimenko.nadia.configuration.RetrofitFactory
import nl.klimenko.nadia.models.Article
import nl.klimenko.nadia.models.ResultArticle
import nl.klimenko.nadia.models.User
import nl.klimenko.nadia.repository.ArticleService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailedArticle : AppCompatActivity(), Callback<Void> {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val item = intent.getSerializableExtra("Article") as Article
        setContentView(R.layout.article_detailed)
        findViewById<TextView>(R.id.titleDetailed).text = item.Title
        findViewById<ImageView>(R.id.imageDetailed).load(item.Image)
        findViewById<TextView>(R.id.textDetailed).movementMethod = LinkMovementMethod.getInstance()
        findViewById<TextView>(R.id.textDetailed).text =
            HtmlCompat.fromHtml(item.Summary.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
        findViewById<TextView>(R.id.linkToTheArticle).text = item.Url.toString()
        if (User.getUser().token?.AuthToken != null) {
            if (!item.IsLiked!!) {
                val buttonHeart = findViewById<ImageView>(R.id.heart)
                buttonHeart.visibility = View.VISIBLE
                buttonHeart.setOnClickListener {
                    Toast.makeText(this, "You clicked on ImageView.", Toast.LENGTH_SHORT).show()
                    val retrofit = RetrofitFactory.getRetrofitObject()
                    var service = retrofit?.create(ArticleService::class.java)
                    service?.likeArticle("Bearer " + User.getUser().token?.AuthToken as String, item.Id)
                        ?.enqueue(this)
                }
            }
                else{
                    findViewById<ImageView>(R.id.heartLike).visibility = View.VISIBLE
                }
            }
            Log.i("Detail", "Open article with title ${item.Title}")
        }

    override fun onFailure(call: Call<Void>, t: Throwable) {
        Log.e("HTTP", "Could not like an article", t)
    }

    override fun onResponse(call: Call<Void>, response: Response<Void>) {
        this.recreate()

    }
}
