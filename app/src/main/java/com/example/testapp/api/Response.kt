package com.example.testapp.api

import com.example.testapp.entity.Specialist
import com.google.gson.annotations.SerializedName

class Response {
    @SerializedName("response")
    val specialistList: ArrayList<Specialist>? = null
}