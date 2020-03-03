package com.example.svea_assignment.model

import com.example.svea_assignment.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject


class PlaceApiService {

    @Inject
    lateinit var api:GetPlacesApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getAllPlaces():  Single<Places> {
        return api.getAllPlaces()
    }
}
