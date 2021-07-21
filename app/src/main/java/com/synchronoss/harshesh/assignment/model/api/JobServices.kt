package com.synchronoss.harshesh.assignment.model.api

import com.synchronoss.harshesh.assignment.`interface`.Constant
import com.synchronoss.harshesh.assignment.model.modelClass.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface JobServices {

    @GET(Constant.CAPITAL)
    fun getKountry(@Query("q") latitude: String,@Query("units") unit: String, @Query("appid") apikey: String): Call<Weather>
}