package com.synchronoss.harshesh.assignment.view

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.util.Log
import com.synchronoss.harshesh.assignment.`interface`.Constant
import com.synchronoss.harshesh.assignment.`interface`.WeatherInterface
import com.synchronoss.harshesh.assignment.model.modelClass.Weather
import com.synchronoss.harshesh.assignment.presenter.WeatherPresenter

class WeatherJobService: JobService() , WeatherInterface.weatherView{
    companion object {
        const val TAG = "WeatherJobService"
    }
    private var jobCanceled : Boolean = false
    private var mWeatherPresenter: WeatherPresenter? = null

    override fun onStartJob(p0: JobParameters?): Boolean {
        Log.d(TAG, "Job Started")
        callWeatherAPI()
        return true
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        Log.d(TAG, "Job canceled before completion")
        jobCanceled = true
        return true
    }

    fun callWeatherAPI(){
        Log.i(TAG, "callWeatherAPI method called")
        if(mWeatherPresenter == null){
            mWeatherPresenter = WeatherPresenter(this)
        }
        Constant.getLocation(this)
        if(!Constant.cityNameLocality.equals("")) {
            mWeatherPresenter?.networkcall(Constant.cityNameLocality!!)
        }
    }

    override fun updateViewData(weather: Weather) {
        Log.i(TAG, "Weather response received and update the UI")
        Constant.successFailedState = 1
        Constant.weatherOBJ = weather!!
        var mIntent = Intent("com.example.assignment")
        mIntent.putExtra(Constant.resultState,Constant.successFailedState)
        sendBroadcast(mIntent)
    }

    override fun failedData(resultCode: Int, errorMessage: String) {
        Log.i(TAG, "Weather response received failed")
        Constant.successFailedState = 2
        var mIntent = Intent("com.example.assignment")
        mIntent.putExtra(Constant.resultState,Constant.successFailedState)
        mIntent.putExtra(Constant.resultCode,resultCode)
        mIntent.putExtra(Constant.errorMessage,errorMessage)
        sendBroadcast(mIntent)
    }
}