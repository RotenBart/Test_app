package com.example.testapp.data

import com.example.testapp.api.ApiHolder
import com.example.testapp.api.Response
import io.reactivex.Single

class Repository {
    fun getSpecialtiesList(): Single<Response> {
        return ApiHolder.getJsonApi().getSpecialtiesList()
    }
}