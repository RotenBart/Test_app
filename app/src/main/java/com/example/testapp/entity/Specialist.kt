package com.example.testapp.entity

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.regex.Pattern

class Specialist {

    var id: Long? = null

    companion object {
        private val DATE_PATTERN_1: Pattern = Pattern.compile(
            "^\\d{4}-\\d{2}-\\d{2}$"
        )
        private val DATE_PATTERN_2: Pattern = Pattern.compile(
            "^\\d{2}-\\d{2}-\\d{4}$"
        )
        private const val DATE_FORMAT_DD_MM_YYYY = "dd-MM-yyyy"
        private const val DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd"
        private const val DATE_FORMAT = "dd.MM.yyyy"
    }


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

    fun getSpecialistAge(): String {
        return if (birthday !=null && birthday != "-") {
            val today = Calendar.getInstance()
            val birthday = Calendar.getInstance()
            val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
            val birthDate = dateFormat.parse(this.birthday.toString())
            if (birthDate != null) {
                birthday.time = birthDate
            }
            var age = today.get(Calendar.YEAR) - birthday.get(Calendar.YEAR)
            if (today.get(Calendar.DAY_OF_YEAR) < birthday.get(Calendar.DAY_OF_YEAR)) {
                age--
            }
            age.toString()
        } else "-"
    }

    fun getReformattedBirthday(): String {
        if (!birthday.isNullOrEmpty()) {
                val oldDateFormat: SimpleDateFormat
                val newDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
                return when {
                    DATE_PATTERN_1.matcher(birthday).matches() -> {
                        oldDateFormat = SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD, Locale.getDefault())
                        newDateFormat.format(oldDateFormat.parse(birthday))
                    }
                    DATE_PATTERN_2.matcher(birthday).matches() -> {
                        oldDateFormat = SimpleDateFormat(DATE_FORMAT_DD_MM_YYYY, Locale.getDefault())
                        newDateFormat.format(oldDateFormat.parse(birthday))
                    }
                    else -> "-"
                }
        } else return "-"
    }

    fun formatSpecialistName() {
        val firstNameFormat = firstName
        val lastNameFormat = lastName
        firstName = firstNameFormat?.toLowerCase(Locale("ru"))?.capitalize(Locale("ru"))
        lastName = lastNameFormat?.toLowerCase(Locale("ru"))?.capitalize(Locale("ru"))
    }
}