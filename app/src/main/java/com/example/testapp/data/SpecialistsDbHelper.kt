package com.example.testapp.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.testapp.entity.Specialty
import com.example.testapp.entity.Specialist
import com.example.testapp.data.SpecialistsContract.SpecialistEntry.AVATAR_URL
import com.example.testapp.data.SpecialistsContract.SpecialistEntry.BIRTHDAY
import com.example.testapp.data.SpecialistsContract.SpecialistEntry.FIRST_NAME
import com.example.testapp.data.SpecialistsContract.SpecialistEntry.LAST_NAME
import com.example.testapp.data.SpecialistsContract.SpecialistEntry.SPECIALTY
import com.example.testapp.data.SpecialistsContract.SpecialistEntry.SPECIALISTS_TABLE_NAME
import com.example.testapp.data.SpecialistsContract.SpecialtyEntry.SPECIALTIES_TABLE_NAME
import com.example.testapp.data.SpecialistsContract.SpecialtyEntry.SPECIALTY_ID
import com.example.testapp.data.SpecialistsContract.SpecialtyEntry.SPECIALTY_NAME

class SpecialistsDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val CREATE_TABLE_SPECIALISTS = "CREATE TABLE $SPECIALISTS_TABLE_NAME (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "$FIRST_NAME TEXT," +
                "$LAST_NAME TEXT," +
                "$BIRTHDAY TEXT," +
                "$AVATAR_URL TEXT," +
                "$SPECIALTY INTEGER)"

        private const val CREATE_TABLE_SPECIALTIES = "CREATE TABLE $SPECIALTIES_TABLE_NAME (" +
                "$SPECIALTY_ID INTEGER PRIMARY KEY," +
                "$SPECIALTY_NAME TEXT)"

        private const val DELETE_TABLE_SPECIALISTS = "DROP TABLE IF EXISTS $SPECIALISTS_TABLE_NAME"
        private const val DELETE_TABLE_SPECIALTIES = "DROP TABLE IF EXISTS $SPECIALTIES_TABLE_NAME"
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Specialists.db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_SPECIALISTS)
        db.execSQL(CREATE_TABLE_SPECIALTIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DELETE_TABLE_SPECIALISTS)
        db.execSQL(DELETE_TABLE_SPECIALTIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun clearDb(){
        val db= this.readableDatabase
        db.execSQL(DELETE_TABLE_SPECIALISTS)
        db.execSQL(DELETE_TABLE_SPECIALTIES)
    }

    fun createSpecialty(specialty: Specialty) {
        val values = ContentValues().apply {
            put(SPECIALTY_ID, specialty.specialtyId)
            put(SPECIALTY_NAME, specialty.specialtyName)
        }
        this.writableDatabase.insert(SPECIALTIES_TABLE_NAME, null, values)
    }

    fun createSpecialist(specialist: Specialist) {
        val values = ContentValues().apply {
            put(FIRST_NAME, specialist.firstName)
            put(LAST_NAME, specialist.lastName)
            put(BIRTHDAY, specialist.birthday)
            put(AVATAR_URL, specialist.avatarUrl)
            put(SPECIALTY, specialist.specialty?.get(0)?.specialtyId)
        }
        specialist.id = this.writableDatabase.insert(SPECIALISTS_TABLE_NAME, null, values)
    }

    fun getAllSpecialistsBySpecialtyId(id: Int?): ArrayList<Specialist> {
        val specialistList = arrayListOf<Specialist>()
        val selectQuery =
            "SELECT * FROM " +
                    SPECIALISTS_TABLE_NAME +
                    " WHERE " +
                    SPECIALTY +
                    " = " + id
        val cursor = this.readableDatabase.rawQuery(selectQuery, null)
        with(cursor) {
            while (moveToNext()) {
                val specialist = Specialist().apply {
                    firstName = cursor.getString(cursor.getColumnIndex(FIRST_NAME))
                    lastName = cursor.getString(cursor.getColumnIndex(LAST_NAME))
                    birthday = cursor.getString(cursor.getColumnIndex(BIRTHDAY))
                    avatarUrl = cursor.getString(cursor.getColumnIndex(AVATAR_URL))
                }
                specialistList.add(specialist)
            }
        }
        return specialistList
    }

    fun getSpecialtyNameById(id: Int): String {
        val specialtyName: String
        val selectQuery =
            "SELECT" + SPECIALTY_NAME + "FROM" + SPECIALTIES_TABLE_NAME + "WHERE" + SPECIALTY_ID + "=" + id
        val cursor = this.readableDatabase.rawQuery(selectQuery, null)
        with(cursor) {
            moveToFirst()
            specialtyName = cursor.getString(cursor.getColumnIndex(SPECIALTY_NAME))
        }
        return specialtyName
    }

    fun getAllSpecialties(): ArrayList<Specialty> {
        val specialtyList = arrayListOf<Specialty>()
        val selectQuery = "SELECT * FROM $SPECIALTIES_TABLE_NAME"
        val cursor = this.readableDatabase.rawQuery(selectQuery, null)
        with(cursor) {
            while (moveToNext()) {
                val specialty = Specialty().apply {
                    specialtyId = cursor.getInt(cursor.getColumnIndex(SPECIALTY_ID))
                    specialtyName = cursor.getString(cursor.getColumnIndex(SPECIALTY_NAME))
                }
                specialtyList.add(specialty)
            }
        }
        return specialtyList
    }
}