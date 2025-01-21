package com.example.finalproject_pam.repository

import com.example.finalproject_pam.model.AllPemilikResponse
import com.example.finalproject_pam.model.Pemilik
import com.example.finalproject_pam.service.PemilikService
import java.io.IOException

interface PemilikRepository {
    suspend fun getPemilik(): AllPemilikResponse
    suspend fun insertPemilik(pemilik: Pemilik)
    suspend fun updatePemilik(id_pemilik: String, pemilik: Pemilik)
    suspend fun deletePemilik(id_pemilik: String)
    suspend fun getPemilikById(id_pemilik: String): Pemilik
}

class NetworkPemilikRepository(
    private val pemilikApiService: PemilikService
) : PemilikRepository {


    override suspend fun insertPemilik(pemilik: Pemilik) {
        pemilikApiService.insertPemilik(pemilik)
    }

    override suspend fun updatePemilik(id_pemilik: String, pemilik: Pemilik) {
        pemilikApiService.updatePemilik(id_pemilik, pemilik)
    }

    override suspend fun deletePemilik(id_pemilik: String) {
        try {
            val response = pemilikApiService.deletePemilik(id_pemilik)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Mahasiswa. HTTP Status code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e:Exception) {
            throw e
        }
    }

    override suspend fun getPemilik(): AllPemilikResponse =
        pemilikApiService.getAllPemilik()

    override suspend fun getPemilikById(id_pemilik: String): Pemilik {
        return pemilikApiService.getPemilikbyid(id_pemilik).data
    }

}
