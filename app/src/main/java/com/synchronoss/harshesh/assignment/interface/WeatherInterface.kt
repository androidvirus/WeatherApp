package com.synchronoss.harshesh.assignment.`interface`

import com.synchronoss.harshesh.assignment.model.modelClass.Weather

interface WeatherInterface {

    interface weatherModel {
        fun getWeatherByCityName(latitude: String, presenter: weatherPresenter)
    }

    interface weatherView {
        fun updateViewData(weather: Weather)
        fun failedData(resultCode: Int, errorMessage: String)
    }

    interface lastView {
        fun updateViewData()
        fun failedData(resultCode: Int, errorMessage: String)
    }

    interface weatherPresenter {
        fun networkcall(latitude: String)
        fun success(weather: Weather)
        fun failed(resultCode: Int, errorMessage: String)
    }
}