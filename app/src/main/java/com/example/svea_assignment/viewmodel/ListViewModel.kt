package com.example.svea_assignment.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.svea_assignment.model.Place
import com.example.svea_assignment.model.PlaceApiService
import com.example.svea_assignment.model.Places
import com.example.svea_assignment.model.ServerResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call

class ListViewModel(applicaiton: Application) : AndroidViewModel(applicaiton) {

    val place by lazy { MutableLiveData<List<Place>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    private val disposable = CompositeDisposable()
    private var apiService = PlaceApiService()

    fun refresh(){

        loading.value = true
        getPlaces()
    }

    private fun getPlaces() {

        disposable.add(
            apiService.getAllPlaces()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ServerResponse>() {
                    override fun onSuccess(list: ServerResponse) {
                        loadError.value = false
                       // place.value = list.places
                      //  println("the list if --->$list")
                        println("the list if --->${list.places}")
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
//        val job = GlobalScope.async(Dispatchers.IO) {
//            apiService.getAllPlaces().execute().body()?.place
//        }
//        GlobalScope.launch(Dispatchers.Main) {
//            val result= job.await()
//            if (result != null) {
//                updateUi(result)
//                // json output [Place(alias=a353a831-5d53-4cc2-93f3-6c8a3df1e3c9, name=Testplats för MO, longitude=17.6422735, latitude=59.8581518, description=Kommer inom kort, icon=/image/83423179e3a34332857e47f7cacdf703_icon_1416923254311), Place(alias=d2ee4e66-cfff-4522-a999-99b0460c535b, name=Arlanda - Pontus in the Air, longitude=17.931236799999965, latitude=59.6513424, description=Kommande, icon=/image/db3f9210-d1dd-4b37-bb83-e23ebe8b422c), Place(alias=b773c20c-5038-4d0e-a216-a647fab1d1a2, name=Evenemangsgatan 31, longitude=17.9996718, latitude=59.3704332, description=., icon=/image/db3f9210-d1dd-4b37-bb83-e23ebe8b422c), Place(alias=d510127e-7bda-4721-b5d7-9e2308f2f90d, name=SERGEL, longitude=18.06319099999996, latitude=59.33394819999999, description=Eat clean - train mean - get lean, icon=/image/eb043be7_46f9_4b94_b133_40bddb4c204a_icon_1441092491212), Place(alias=e6255fac-4ca2-482e-be71-39e4e76d11a4, name=Storgatan är stängd p.g.a. renovering, longitude=22.1418165, latitude=65.5831446, description=Hej kära gäster!
//            }
//        }
    }

    private fun updateUi(result: List<Place>) {
        place.value = result
        loadError.value = false
        loading.value = false
    }
}