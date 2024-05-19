package com.example.sportapp.networking

import com.google.gson.annotations.SerializedName

class SportResponse {

    @SerializedName("i")
    val sportId:String? = null //TODO

    @SerializedName("d")
    val sportName:String? = null

    @SerializedName("e")
    val activeEvents:List<Event>? = null
}