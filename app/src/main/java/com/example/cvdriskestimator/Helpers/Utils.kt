package com.example.cvdriskestimator.Helpers

import com.google.gson.Gson
import com.google.gson.GsonBuilder


object Utils {
    private var gson: Gson? = null
    val getGsonParser: Gson?
        get() {
            if (null == gson) {
                val builder = GsonBuilder()
                gson = builder.create()
            }
            return gson
        }
}