package com.synchronoss.harshesh.assignment.model.modelClass


import com.google.gson.annotations.SerializedName


data class Sys(

	@field:SerializedName("type")
	val type: Int? = null,

	@field:SerializedName("id")
	val id: Double? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("sunrise")
	val sunrise: Double? = null,

	@field:SerializedName("sunset")
	val sunset: Double? = null,

)