package nl.klimenko.nadia.configuration

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitFactory {
    private var retrofit: Retrofit? = null

    private fun buildRetrofit() {
        retrofit = Retrofit.Builder()
            .baseUrl("https://inhollandbackend.azurewebsites.net/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    fun getRetrofitObject(): Retrofit? {
        if (retrofit == null) {
            buildRetrofit()
            return retrofit
        } else {
            return retrofit
        }
    }
}