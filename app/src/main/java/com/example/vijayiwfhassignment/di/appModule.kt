package com.example.vijayiwfhassignment.di

import TitlesRepositoryImpl
import com.example.vijayiwfhassignment.data.api.WatchmodeApiService
import com.example.vijayiwfhassignment.domain.repo.TitlesRepository
import com.example.vijayiwfhassignment.presentation.screens.detail_screen.DetailsViewModel
import com.example.vijayiwfhassignment.presentation.screens.home_screen.HomeViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory

val appModule = module {

    single {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val contentType = "application/json".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
            isLenient = true
        }

        Retrofit.Builder()
            .baseUrl(WatchmodeApiService.BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(WatchmodeApiService::class.java) }

    single<TitlesRepository> { TitlesRepositoryImpl(apiService = get()) }


    viewModel { HomeViewModel(repository = get()) }

    viewModel { params ->
        val titleId = params.getOrNull<Int>() ?: 0
        DetailsViewModel(titleId = titleId, repository = get())
    }
}