package com.example.svea_assignment.di

import com.example.svea_assignment.model.GetPlacesApi
import com.example.svea_assignment.model.PlaceApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
open class ApiModule {
    private val BASE_URL = "https://secure.closepayment.com/close-admin/1.0/place/"
    @Provides
    fun providePlaceApi():GetPlacesApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(GetPlacesApi::class.java)
    }
    @Provides
    open fun providePlaceApiService():PlaceApiService{
        return PlaceApiService()
    }
}