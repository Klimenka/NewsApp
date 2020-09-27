package nl.klimenko.nadia.services

import nl.klimenko.nadia.models.ResultArticle
import retrofit2.Call
import retrofit2.http.GET

interface ArticleService {
    @GET("api/Articles")
    fun articles(): Call<ResultArticle>
}