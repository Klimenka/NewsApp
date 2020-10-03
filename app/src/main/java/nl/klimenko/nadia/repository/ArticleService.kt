package nl.klimenko.nadia.repository

import nl.klimenko.nadia.models.Feed
import nl.klimenko.nadia.models.ResultArticle
import retrofit2.Call
import retrofit2.http.*

interface ArticleService {

    @GET("api/Articles")
    fun articles(@Header("x-authtoken") token: String?): Call<ResultArticle>

    //delete this if I do not use it
    @GET("api/Feeds")
    fun feeds(): Call<List<Feed>>

    @GET("api/Articles/{id}")
    fun nextArticles(
        @Header("x-authtoken") token: String?,
        @Path("id") id: Int,
        @Query("count") count: Int
    ): Call<ResultArticle>

    @PUT("api/Articles/{id}/like")
    fun likeArticle(
        @Header("x-authtoken") token: String,
        @Path("id") id: Int
    ): Call<Void>
}