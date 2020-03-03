package com.example.svea_assignment.di

import com.example.svea_assignment.model.PlaceApiService
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service: PlaceApiService)
}