package com.example.pixabay

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PixaApi {
    @GET("api/")
    fun getImages(@Query("key")key:String = "25007027-7418deb977c638792f4bfb99f",
                  @Query("q") keyWordForSearch: String,
                  @Query("per_page") perPage: Int = 5,
                  @Query("page") page:Int = 1,
    ): Call<PixabayModel>
}