package com.example.finalproject_pam.dependenciesinjection

import com.example.finalproject_pam.repository.NetworkPemilikRepository
import com.example.finalproject_pam.repository.PemilikRepository
import com.example.finalproject_pam.service.PemilikService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import kotlinx.serialization.json.Json
import retrofit2.Retrofit

interface AppContainer{
    val pemilikRepository: PemilikRepository
}

class PemilikContainer : AppContainer{
    private val baseUrl = "http://10.0.2.2:3000/api/pemilik/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val pemilikService: PemilikService by lazy {
        retrofit.create(PemilikService::class.java)
    }
    override val pemilikRepository: PemilikRepository by lazy {
        NetworkPemilikRepository(pemilikService)
    }
}