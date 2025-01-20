package com.example.finalproject_pam.repository

import com.example.finalproject_pam.model.AllPemilikResponse
import com.example.finalproject_pam.model.Pemilik
import java.io.IOException

interface PemilikRepository {
    suspend fun getPemilik(): AllPemilikResponse
    suspend fun insertPemilik(pemilik: Pemilik)
    suspend fun updatePemilik(nim: String, pemilik: Pemilik)
    suspend fun deletePemilik(nim: String)
    suspend fun getPemilikByNim(nim: String): Pemilik
}

class NetworkPemilikRepository(
    private val pemilikApiService: PemilikService
) : PemilikRepository {


    override suspend fun insertPemilik(pemilik: Pemilik) {
        pemilikApiService.insertPemilik(pemilik)
    }

    override suspend fun updatePemilik(nim: String, pemilik: Pemilik) {
        pemilikApiService.updatePemilik(nim, pemilik)
    }

    override suspend fun deletePemilik(nim: String) {
        try {
            val response = pemilikApiService.deletePemilik(nim)
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

    override suspend fun getPemilikByNim(nim: String): Pemilik {
        return pemilikApiService.getPemilikbyNim(nim).data
    }

}
