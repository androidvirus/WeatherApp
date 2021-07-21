package com.synchronoss.harshesh.assignment.model.repos

import android.util.Log
import com.synchronoss.harshesh.assignment.`interface`.Constant
import com.synchronoss.harshesh.assignment.`interface`.WeatherInterface
import com.synchronoss.harshesh.assignment.model.api.JobAPI
import com.synchronoss.harshesh.assignment.model.api.JobServices
import com.synchronoss.harshesh.assignment.model.modelClass.Weather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepo : WeatherInterface.weatherModel {
    private var apiclient: JobServices? = null
    init {
        apiclient = JobAPI.client.create(JobServices::class.java)
    }

    override fun getWeatherByCityName(
        latitude: String,
        presenter: WeatherInterface.weatherPresenter) {
        val call = apiclient?.getKountry(latitude,"metric", Constant.APIKEY)

        call?.enqueue(object : Callback<Weather> {
            override fun onFailure(call: Call<Weather>?, t: Throwable?) {
                Log.d("failed", t.toString())
                presenter.failed(-1,t.toString())
            }
            override fun onResponse(
                call: Call<Weather>?,
                response: Response<Weather>?
            ) {
                if (response?.isSuccessful!!) {
                    var results = response?.body()
                    Log.d("success", results.toString())
                    presenter.success(results!!)
                } else {
                    presenter.failed(response.code(), response.body().toString())
                }
            }
        })
    }
}