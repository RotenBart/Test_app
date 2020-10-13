package com.example.testapp.data

import android.provider.BaseColumns

object SpecialistsContract {
    object SpecialistEntry: BaseColumns {
        const val ID = "id"
        const val SPECIALISTS_TABLE_NAME = "specialists"
        const val FIRST_NAME = "first_name"
        const val LAST_NAME = "last_name"
        const val BIRTHDAY = "birthday"
        const val AVATAR_URL = "avatar_url"
        const val SPECIALTY = "specialty_id"
    }

    object SpecialtyEntry: BaseColumns {
        const val SPECIALTIES_TABLE_NAME = "specialties"
        const val SPECIALTY_ID = "id"
        const val SPECIALTY_NAME = "name"
    }
}