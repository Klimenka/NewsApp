package nl.klimenko.nadia.repository

import nl.klimenko.nadia.models.RegisterMessage
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded

interface RegisterService {
    @FormUrlEncoded
    @POST("api/Users/register")
    fun register(@Field ("UserName") UserName : String, @Field ("Password") Password : String) : Call<RegisterMessage>
}