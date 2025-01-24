package com.example.session12.dependenciesinjection

import com.example.finalproject_pam.repository.JenisRepository
import com.example.finalproject_pam.repository.NetworkJenisRepository
import com.example.finalproject_pam.repository.NetworkPemilikRepository
import com.example.finalproject_pam.repository.PemilikRepository
import com.example.finalproject_pam.service.JenisService
import com.example.finalproject_pam.service.ManajerService
import com.example.finalproject_pam.service.PemilikService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit


interface AppContainer{
    val pemilikRepository: PemilikRepository
    val jenisRepository: JenisRepository
    val manajerRepository: JenisRepository
}

class Container : AppContainer{
    private val baseUrl = "http://10.0.2.2:3080/api/"
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

    private val jenisService: JenisService by lazy {
        retrofit.create(JenisService::class.java)
    }
    override val jenisRepository: JenisRepository by lazy {
        NetworkJenisRepository(jenisService)
    }
    private val manajerService: JenisService by lazy {
        retrofit.create(ManajerService::class.java)
    }
    override val manajerRepository: JenisRepository by lazy {
        NetworkJenisRepository(ManajerService)
    }
}