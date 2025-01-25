package com.example.finalproject_pam.service

import com.example.finalproject_pam.model.AllManajerResponse
import com.example.finalproject_pam.model.Manajer
import com.example.finalproject_pam.model.ManajerDetailResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ManajerService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    //@GET
    @GET("manajer")
    suspend fun getAllManajer(): AllManajerResponse

    //@GET
    @GET("manajer/{id_manajer}")
    suspend fun getManajerById(@Path("id_manajer")id_manajer: String): ManajerDetailResponse

    //@POST
    @POST("manajer/manajer")
    suspend fun insertManajer(@Body manajer: Manajer)


    @PUT("manajer/{id_manajer}")
    suspend fun updateManajer(@Path("id_manajer")id_manajer: String, @Body manajer: Manajer)

    @DELETE("manajer/{id_manajer}")
    suspend fun deleteManajer(@Path("id_manajer")id_manajer: String):retrofit2.Response<Void>
}