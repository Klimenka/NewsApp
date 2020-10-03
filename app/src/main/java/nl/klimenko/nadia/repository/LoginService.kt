package nl.klimenko.nadia.repository

import nl.klimenko.nadia.models.LoginToken
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService {
    @FormUrlEncoded
    @POST("api/Users/login")
    fun login(
        @Field("UserName") UserName : String,
        @Field("Password") Password : String
    ) : Call<LoginToken>
}