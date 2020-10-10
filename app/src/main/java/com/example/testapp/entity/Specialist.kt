package com.example.testapp.entity

import com.google.gson.annotations.SerializedName
import java.util.*

class Specialist {

    var id: Long? = null

    @SerializedName("avatr_url")
    var avatarUrl: String? = null

    @SerializedName("birthday")
    var birthday: String? = null

    @SerializedName("f_name")
    var firstName: String? = null

    @SerializedName("l_name")
    var lastName: String? = null

    @SerializedName("specialty")
    var specialty: List<Specialty>? = null

    fun getSpecialistAge(){
        val currentDate = Calendar.getInstance().timeInMillis

    }
}