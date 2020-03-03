package com.example.svea_assignment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.svea_assignment.di.DaggerViewModelComponent
import com.example.svea_assignment.model.Place
import com.example.svea_assignment.model.PlaceApiService
import com.example.svea_assignment.model.Places
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class ListViewModel(applicaiton: Application) : AndroidViewModel(applicaiton) {
    constructor(applicaiton: Application,test:Boolean=true):this(applicaiton){
        injected=true
    }

    val place by lazy { MutableLiveData<List<Place>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    private val disposable = CompositeDisposable()
    @Inject
    lateinit var apiService :PlaceApiService

    private var injected=false
    fun inject() {
        if(!injected){
            DaggerViewModelComponent.create().inject(this)
        }
    }

    fun refresh(){
        inject()
        loading.value = true
        getPlaces()
    }

    private fun getPlaces() {
        disposable.add(
            apiService.getAllPlaces()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Places>() {
                    override fun onSuccess(list: Places) {
                        loadError.value = false
                        place.value = list.place
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        loading.value = false
                        place.value = null
                        loadError.value = true

                    }
                })
        )
    }
}