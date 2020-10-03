package nl.klimenko.nadia.repository

import nl.klimenko.nadia.models.Feed
import nl.klimenko.nadia.models.ResultArticle
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArticleService {
    @GET("api/Articles")
    fun articles(): Call<ResultArticle>

    //delete this if I do not use it
    @GET("api/Feeds")
    fun feeds(): Call<List<Feed>>

    @GET("api/Articles/{id}")
    fun nextArticles(@Path("id") id : Int, @Query("count") count : Int) : Call<ResultArticle>
}