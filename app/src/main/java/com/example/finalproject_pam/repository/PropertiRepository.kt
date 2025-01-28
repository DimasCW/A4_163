package com.example.finalproject_pam.repository

import com.example.finalproject_pam.model.AllPropertiResponse
import com.example.finalproject_pam.model.Jenis
import com.example.finalproject_pam.model.Manajer
import com.example.finalproject_pam.model.Pemilik
import com.example.finalproject_pam.model.Properti
import com.example.finalproject_pam.service.PropertiService
import okio.IOException


interface PropertiRepository {
    suspend fun getProperti(): AllPropertiResponse
    suspend fun insertProperti(properti: Properti)
    suspend fun updateProperti(id_properti: String, properti: Properti)
    suspend fun deleteProperti(id_properti: String)
    suspend fun getPropertiById(id_properti: String): Properti
}

class NetworkPropertiRepository(
    private val propertiApiService: PropertiService
) :PropertiRepository {


    override suspend fun insertProperti(properti: Properti) {
        propertiApiService.insertProperti(properti)
    }

    override suspend fun updateProperti(id_properti: String, properti: Properti) {
        propertiApiService.updateProperti(id_properti, properti)
    }

    override suspend fun deleteProperti(id_properti: String) {
        try {
            val response = propertiApiService.deleteProperti(id_properti)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Properti. HTTP Status code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e:Exception) {
            throw e
        }
    }

    override suspend fun getProperti(): AllPropertiResponse =
        propertiApiService.getAllProperti()

    override suspend fun getPropertiById(id_properti: String): Properti {
        return propertiApiService.getPropertiById(id_properti).data
    }


}
