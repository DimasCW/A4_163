package com.example.session12.dependenciesinjection

import com.example.finalproject_pam.service.PemilikService
import com.example.session12.repository.NetworkPemilikRepository
import com.example.session12.repository.PemilikRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit


interface AppContainer{
    val pemilikRepository: PemilikRepository
}

class PemilikContainer : AppContainer{
    private val baseUrl = "http://10.0.2.2:3080/api/pemilik/"
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