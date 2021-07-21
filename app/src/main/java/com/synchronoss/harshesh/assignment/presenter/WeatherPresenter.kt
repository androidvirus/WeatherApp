package com.synchronoss.harshesh.assignment.presenter

import com.synchronoss.harshesh.assignment.`interface`.WeatherInterface
import com.synchronoss.harshesh.assignment.model.modelClass.Weather
import com.synchronoss.harshesh.assignment.model.repos.WeatherRepo

class WeatherPresenter() : WeatherInterface.weatherPresenter {

    private lateinit var view: WeatherInterface.weatherView
    private var model: WeatherInterface.weatherModel = WeatherRepo()

    constructor(countryView: WeatherInterface.weatherView) : this() {
        // some code
        view = countryView
    }
    override fun success(weather: Weather) {
        view.updateViewData(weather)
    }

    override fun failed(resultCode: Int, errorMessage: String) {
        view.failedData(resultCode,errorMessage)
    }

    override fun networkcall(latitude: String) {
        model?.getWeatherByCityName(latitude, this)
    }

}