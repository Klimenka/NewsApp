package nl.klimenko.nadia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import nl.klimenko.nadia.models.ResultArticle
import nl.klimenko.nadia.services.ArticleService
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity(), Callback<ResultArticle> {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
            val articles= response.body() as ResultArticle
           // this.activity_main.layoutManager = LinearLayoutManager(this)
           // this.recyclerview.adapter = MyAdapter(this, quotes)
        }
    }
}