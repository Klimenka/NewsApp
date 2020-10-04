package nl.klimenko.nadia.views

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.transition.Explode
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import coil.load
import nl.klimenko.nadia.R
import nl.klimenko.nadia.configuration.RetrofitFactory
import nl.klimenko.nadia.models.Article
import nl.klimenko.nadia.repository.ArticleService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailedArticle : AppCompatActivity(), Callback<Void> {
    lateinit var article : Article
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(window) {
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
            enterTransition = Explode()
            exitTransition = Explode()
            exitTransition.duration = 2000
        }
        article = intent.getSerializableExtra("Article") as Article
        val token = intent.getSerializableExtra("Token") as String?
        setContentView(R.layout.article_detailed)
        findViewById<TextView>(R.id.titleDetailed).text = article.Title
        findViewById<ImageView>(R.id.imageDetailed).load(article.Image)
        findViewById<TextView>(R.id.textDetailed).movementMethod = LinkMovementMethod.getInstance()
        findViewById<TextView>(R.id.textDetailed).text =
            HtmlCompat.fromHtml(article.Summary.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
        findViewById<TextView>(R.id.linkToTheArticle).text = article.Url.toString()
        if (token != null) {
            if (!article.IsLiked!!) {
                val buttonHeart = findViewById<ImageView>(R.id.heart)
                buttonHeart.visibility = View.VISIBLE
                buttonHeart.setOnClickListener {
                    val retrofit = RetrofitFactory.getRetrofitObject()
                    val service = retrofit?.create(ArticleService::class.java)
                    service?.likeArticle(token, article.Id)
                        ?.enqueue(this)
                    article.IsLiked = true
                }
            } else {
                val buttonHeart = findViewById<ImageView>(R.id.heartLike)
                buttonHeart.visibility = View.VISIBLE
                buttonHeart.setOnClickListener {
                    val retrofit = RetrofitFactory.getRetrofitObject()
                    val service = retrofit?.create(ArticleService::class.java)
                    service?.dislikeArticle(token, article.Id)
                        ?.enqueue(this)
                    article.IsLiked = false
                }
            }
        }
        Log.i("Detail", "Open article with title ${article.Title}")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }
    override fun onFailure(call: Call<Void>, t: Throwable) {

        Log.e("HTTP", "Could not like an article", t)
        Toast.makeText(this, this.getString(R.string.wrong), Toast.LENGTH_SHORT).show()
        article.IsLiked = !(article.IsLiked as Boolean)
    }

    override fun onResponse(call: Call<Void>, response: Response<Void>) {
        this.recreate()
    }

}
