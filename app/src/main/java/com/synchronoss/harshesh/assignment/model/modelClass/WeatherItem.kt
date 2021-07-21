package com.synchronoss.harshesh.assignment.model.modelClass

import com.google.gson.annotations.SerializedName

data class WeatherItem(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("main")
    val main: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("icon")
    val icon: String? = null
)