package com.example.testapp.api

import io.reactivex.Single
import retrofit2.http.GET

interface JsonApi {
    @GET("65gb/static/raw/master/testTask.json/")
    fun getSpecialtiesList(): Single<Response>
}