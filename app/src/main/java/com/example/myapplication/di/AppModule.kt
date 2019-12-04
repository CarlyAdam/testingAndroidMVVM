package com.example.myapplication.di

import com.example.myapplication.data.ApiService
import com.example.myapplication.repo.PersonRepository
import com.example.myapplication.utils.NetworkConnectionInterceptor
import com.example.myapplication.viewmodel.PersonViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {
    // just declare it
    val myModule = module {
        single { ApiService(get()) }
        single { NetworkConnectionInterceptor(get()) }
        single { PersonRepository(get()) }
        viewModel { PersonViewModel(get()) }

    }

}