package com.example.finalproject_pam.repository

import com.example.finalproject_pam.model.AllManajerResponse
import com.example.finalproject_pam.model.Manajer
import com.example.finalproject_pam.service.ManajerService
import okio.IOException


interface ManajerRepository {
    suspend fun getManajer(): AllManajerResponse
    suspend fun insertManajer(manajer: Manajer)
    suspend fun updateManajer(id_manajer: String, manajer: Manajer)
    suspend fun deleteManajer(id_manajer: String)
    suspend fun getManajerById(id_manajer: String): Manajer
}

class NetworkManajerRepository(
    private val manajerApiService: ManajerService
) :ManajerRepository {


    override suspend fun insertManajer(manajer: Manajer) {
        manajerApiService.insertManajer(manajer)
    }

    override suspend fun updateManajer(id_manajer: String, manajer: Manajer) {
        manajerApiService.updateManajer(id_manajer, manajer)
    }

    override suspend fun deleteManajer(id_manajer: String) {
        try {
            val response = manajerApiService.deleteManajer(id_manajer)
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

    override suspend fun getManajer(): AllManajerResponse =
        manajerApiService.getAllManajer()

    override suspend fun getManajerById(id_manajer: String): Manajer {
        return manajerApiService.getManajerById(id_manajer).data
    }

}