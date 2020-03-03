package com.example.svea_assignment.di

import com.example.svea_assignment.viewmodel.ListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ViewModelComponent {
    fun inject(viewModel: ListViewModel)
}