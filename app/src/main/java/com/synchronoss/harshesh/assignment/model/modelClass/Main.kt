package com.synchronoss.harshesh.assignment.model.modelClass


import com.google.gson.annotations.SerializedName


data class Main(

	@field:SerializedName("temp")
	val temp: Double? = null,

	@field:SerializedName("feels_like")
	val feels_like: Double? = null,

	@field:SerializedName("temp_min")
	val temp_min: Double? = null,

	@field:SerializedName("temp_max")
	val temp_max: Double? = null,

	@field:SerializedName("pressure")
	val pressure: Double? = null,

	@field:SerializedName("humidity")
	val humidity: Double? = null,
)