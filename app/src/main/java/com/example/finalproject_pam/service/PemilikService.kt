package com.example.finalproject_pam.service

import com.example.finalproject_pam.model.AllPemilikResponse
import com.example.finalproject_pam.model.Pemilik
import com.example.finalproject_pam.model.PemilikDetailResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PemilikService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    //@GET("bacapemilik.php")
    @GET(".")
    suspend fun getAllPemilik(): AllPemilikResponse

    //@GET("baca1pemilik.php")
    @GET("{id_pemilik}")
    suspend fun getPemilikbyid(@Path("id_pemilik")id_pemilik: String): PemilikDetailResponse

    //@POST("insertpemilik.php")
    @POST("pemilik")
    suspend fun insertPemilik(@Body pemilik: Pemilik)

    //@PUT ("editpemilik.php/{id_pemilik}")
    @PUT("{id_pemilik}")
    suspend fun updatePemilik(@Path("id_pemilik")id_pemilik: String, @Body pemilik: Pemilik)

    //@DELETE("deletepemilik.php/{id_pemilik}")
    @DELETE("{id_pemilik}")
    suspend fun deletePemilik(@Path("id_pemilik")id_pemilik: String):retrofit2.Response<Void>
}