package com.synchronoss.harshesh.assignment.model.modelClass


import com.google.gson.annotations.SerializedName


data class Wind(

	@field:SerializedName("speed")
	val speed: Double? = null,

	@field:SerializedName("deg")
	val deg: Double? = null,

)