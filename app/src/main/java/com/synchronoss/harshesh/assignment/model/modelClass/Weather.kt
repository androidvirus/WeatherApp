package com.synchronoss.harshesh.assignment.model.modelClass

import com.google.gson.annotations.SerializedName

data class Weather (

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("timezone")
	val timezone: Double? = null,

	@field:SerializedName("cod")
	val cod: Double? = null,

	@field:SerializedName("id")
	val id: Double? = null,

	@field:SerializedName("main")
	val mains: Main? = null,

	@field:SerializedName("wind")
	val winds: Wind? = null,

	@field:SerializedName("clouds")
	val clouds: Clouds? = null,

	@field:SerializedName("sys")
	val sys: Sys? = null,

	@field:SerializedName("weather")
	val weather: List<WeatherItem?>? = null
)