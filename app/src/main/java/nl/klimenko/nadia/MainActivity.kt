package nl.klimenko.nadia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.recyclerview.*
import nl.klimenko.nadia.controllers.ArticleListener
import nl.klimenko.nadia.controllers.MyAdapter
import nl.klimenko.nadia.models.Article
import nl.klimenko.nadia.models.ResultArticle
import nl.klimenko.nadia.services.ArticleService
import nl.klimenko.nadia.views.DetailedArticle

import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity(), Callback<ResultArticle> {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview)
        println("hello")

        val retrofit = Retrofit.Builder()
            .baseUrl("https://inhollandbackend.azurewebsites.net/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        println("hello again")
        val service = retrofit.create(ArticleService::class.java)
        service.articles().enqueue(this)
    }

    override fun onFailure(call: Call<ResultArticle>, t: Throwable) {
        Log.e("HTTP", "Could not fetch data", t)
    }

    override fun onResponse(call: Call<ResultArticle>, response: Response<ResultArticle>) {
        if (response.isSuccessful && response.body() != null) {
            println(response.body())
            val intent = Intent(this, DetailedArticle::class.java)
            val articles= response.body()!!.Results as List<Article>
            this.recyclerview.layoutManager = LinearLayoutManager(this)
            this.recyclerview.adapter = MyAdapter(this, articles, object: ArticleListener{
                override fun onArticleClicked(article: Article) {
                    intent.putExtra("Article", article)
                    startActivity(intent)
                }
            })
        }
    }
}