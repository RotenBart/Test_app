package com.example.testapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapp.data.Repository
import com.example.testapp.entity.Specialist
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel : ViewModel() {

    private val repository = Repository()
    var specialistListLiveData: MutableLiveData<ResultState<ArrayList<Specialist>>> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()

    fun getSpecialistList() {
        specialistListLiveData.postValue(ResultState.Loading)
        compositeDisposable.add(repository.getSpecialtiesList().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({ list ->
                if (list.specialistList != null) {
                    specialistListLiveData.postValue(ResultState.Success(list.specialistList))
                }
            }, { error ->
                specialistListLiveData.postValue(ResultState.Error(error))
            }
            )
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}