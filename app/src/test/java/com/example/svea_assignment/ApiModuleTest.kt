package com.example.svea_assignment

import com.example.svea_assignment.di.ApiModule
import com.example.svea_assignment.model.PlaceApiService

class ApiModuleTest(val mockService: PlaceApiService) : ApiModule() {
    override fun providePlaceApiService(): PlaceApiService {
        return mockService
    }
}