package com.example.sportapp.networking

import com.google.gson.annotations.SerializedName

class Event {

    @SerializedName("i")
    val eventId:String? = null

    @SerializedName("si")
    val eventSportId:String? = null

    @SerializedName("d")
    val eventName:String? = null

    @SerializedName("tt")
    val eventStartTime:Int? = null
}