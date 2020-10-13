package com.example.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.testapp.data.SpecialistsDbHelper
import com.example.testapp.entity.Specialist
import com.example.testapp.entity.Specialty
import com.example.testapp.viewmodel.MainActivityViewModel
import com.example.testapp.viewmodel.ResultState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private var specialistList = arrayListOf<Specialist>()
    private var specialtyList = arrayListOf<Specialty>()
    private var viewModel: MainActivityViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        viewModel?.getSpecialistList()
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel?.specialistListLiveData?.observe(this, Observer { state ->
            when (state) {
                is ResultState.Loading -> Toast.makeText(
                    baseContext,
                    "Загрузка данных...",
                    Toast.LENGTH_SHORT
                ).show()
                is ResultState.Success -> {
                    specialistList = state.data
                    specialtyList = getSpecialtyList(specialistList)
                    createDb()
                }
                is ResultState.Error -> Toast.makeText(
                    baseContext,
                    state.exception.localizedMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun createDb() {
        val dbHelper = SpecialistsDbHelper(this)
        clearDb()
        specialistList.forEach { specialist ->
            dbHelper.createSpecialist(specialist)
        }
        specialtyList.forEach { specialty ->
            dbHelper.createSpecialty(specialty)
        }
        showSpecialtyListFragment()
    }

    private fun clearDb() {
        val dbHelper = SpecialistsDbHelper(this)
        dbHelper.clearDb()
    }

    private fun getSpecialtyList(specialistList: ArrayList<Specialist>): ArrayList<Specialty> {
        val specialtyList = arrayListOf<Specialty>()
        specialistList.forEach { specialist ->
            specialist.specialty.let { specialty ->
                if (specialty?.get(0) != null && !specialtyList.contains(specialty[0])) {
                    specialtyList.add(specialty[0])
                }
            }
        }
        return specialtyList
    }

    private fun showSpecialtyListFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, SpecialtiesListFragment()).commit()
    }

    override fun onBackPressed() {
        supportFragmentManager.popBackStack()
    }
}