package com.example.finalproject_pam.repository

import com.example.finalproject_pam.model.AllJenisResponse
import com.example.finalproject_pam.model.AllPemilikResponse
import com.example.finalproject_pam.model.Jenis
import com.example.finalproject_pam.service.JenisService
import okio.IOException


interface JenisRepository {
    suspend fun getJenis(): AllJenisResponse
    suspend fun insertJenis(jenis: Jenis)
    suspend fun updateJenis(id_jenis: String, jenis: Jenis)
    suspend fun deleteJenis(id_jenis: String)
    suspend fun getJenisById(id_jenis: String): Jenis
}

class NetworkJenisRepository(
    private val jenisApiService: JenisService
) :JenisRepository {


    override suspend fun insertJenis(jenis: Jenis) {
        jenisApiService.insertJenis(jenis)
    }

    override suspend fun updateJenis(id_jenis: String, jenis: Jenis) {
        jenisApiService.updateJenis(id_jenis, jenis)
    }

    override suspend fun deleteJenis(id_jenis: String) {
        try {
            val response = jenisApiService.deleteJenis(id_jenis)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete jenis. HTTP Status code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e:Exception) {
            throw e
        }
    }

    override suspend fun getJenis(): AllJenisResponse =
        jenisApiService.getAllJenis()

    override suspend fun getJenisById(id_jenis: String): Jenis {
        return jenisApiService.getJenisById(id_jenis).data
    }

}