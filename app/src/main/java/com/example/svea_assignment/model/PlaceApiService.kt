package com.example.svea_assignment.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class PlaceApiService {
   private val BASE_URL = "https://secure.closepayment.com/close-admin/1.0/place/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(GetPlacesApi::class.java)

    fun getAllPlaces():  Single<Places> {
        return api.getAllPlaces()
    }
}
