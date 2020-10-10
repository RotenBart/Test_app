package com.example.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.testapp.api.ApiHolder
import com.example.testapp.data.SpecialistsDbHelper
import com.example.testapp.entity.Specialist
import com.example.testapp.entity.Specialty
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private var specialistList = arrayListOf<Specialist>()
    private var specialtyList = arrayListOf<Specialty>()
    private var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        compositeDisposable.add(ApiHolder.getJsonApi().getSpecialtiesList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list ->
                    if (list.specialistList != null) {
                        specialistList = list.specialistList
                        specialtyList = getSpecialtyList(list.specialistList)
                        createDb()
                    }
                    val specialist = list.specialistList?.get(1)
                }, { error ->
                    Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            )
        )
    }

//    override fun onDestroy() {
//        clearDb()
//        super.onDestroy()
//    }

    private fun createDb() {
        val dbHelper = SpecialistsDbHelper(this)
        dbHelper.writableDatabase
        specialistList.forEach { specialist ->
            dbHelper.createSpecialist(specialist)
        }
        specialtyList.forEach { specialty ->
            dbHelper.createSpecialty(specialty)
        }
        showSpecialtyListFragment()
    }

    private fun clearDb(){
        val dbHelper = SpecialistsDbHelper(this)
        dbHelper.clearDb()
    }

    private fun getSpecialtyList(specialistList: ArrayList<Specialist>): ArrayList<Specialty> {
        val specialtyList = arrayListOf<Specialty>()
        specialistList.forEach { specialist ->
            specialist.specialty.let { specialty ->
                if(specialty?.get(0) != null && !specialtyList.contains(specialty[0])) {
                    specialtyList.add(specialty[0])
                }
            }
        }
        return specialtyList
    }

    private fun showSpecialtyListFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, SpecialtiesListFragment()).commit()
    }

    override fun onBackPressed() {
        supportFragmentManager.popBackStack()
    }
}