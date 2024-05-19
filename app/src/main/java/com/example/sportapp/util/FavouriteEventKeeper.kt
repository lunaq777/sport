package com.example.sportapp.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FavouriteEventKeeper {

    fun saveSportOrEvent(context: Context, key: String, eventOrSportId: String, isSaved: Boolean) {
        var list = getSavedSportsOrEvents(context, key)
        if (list == null) {
            list = ArrayList()
        }
        if (isSaved) {
            list.add(eventOrSportId)
        } else {
            if (list.contains(eventOrSportId)) {
                list.remove(eventOrSportId)
            }
        }
        val prefs = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }

    fun getSavedSportsOrEvents(context: Context, key: String): ArrayList<String>? {
        val prefs = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString(key, null)
        val type = object : TypeToken<ArrayList<String?>?>() {}.type
        return gson.fromJson(json, type) as ArrayList<String>?
    }

    companion object {
        const val EVENTS_KEY = "event"
        const val SPORTS_KEY = "sport"
    }
}