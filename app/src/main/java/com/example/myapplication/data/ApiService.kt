package com.example.myapplication.data

import com.example.myapplication.data.pojo.person
import com.example.myapplication.data.pojo.personResponse
import com.example.myapplication.utils.Constant.API_URL
import com.example.myapplication.utils.NetworkConnectionInterceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("person.json")
    suspend fun getPerson(): Response<personResponse>

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
                    @Field("username") username:String,
                    @Field("password") password:String
    ):Response<String>


    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): ApiService {

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }

}