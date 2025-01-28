package com.example.finalproject_pam.service

import com.example.finalproject_pam.model.AllPropertiResponse
import com.example.finalproject_pam.model.Jenis
import com.example.finalproject_pam.model.Manajer
import com.example.finalproject_pam.model.Pemilik
import com.example.finalproject_pam.model.Properti
import com.example.finalproject_pam.model.PropertiDetailResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface PropertiService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    //@GET
    @GET("properti")
    suspend fun getAllProperti(): AllPropertiResponse

    //@GET
    @GET("properti/{id_properti}")
    suspend fun getPropertiById(@Path("id_properti")id_properti: String): PropertiDetailResponse

    //@POST
    @POST("properti/properti")
    suspend fun insertProperti(@Body properti: Properti)


    @PUT("properti/{id_properti}")
    suspend fun updateProperti(@Path("id_properti")id_properti: String, @Body properti: Properti)

    @DELETE("properti/{id_properti}")
    suspend fun deleteProperti(@Path("id_properti")id_properti: String):retrofit2.Response<Void>


}