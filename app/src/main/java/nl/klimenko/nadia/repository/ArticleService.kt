package nl.klimenko.nadia.repository

import nl.klimenko.nadia.models.ResultArticle
import retrofit2.Call
import retrofit2.http.*

interface ArticleService {

    @GET("api/Articles")
    fun articles(@Header("x-authtoken") token: String?): Call<ResultArticle>

    @GET("api/Articles/liked")
    fun likedArticles(@Header("x-authtoken") token: String?): Call<ResultArticle>

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

    @DELETE("api/Articles/{id}/like")
    fun dislikeArticle(
        @Header("x-authtoken") token: String,
        @Path("id") id: Int
    ): Call<Void>
}