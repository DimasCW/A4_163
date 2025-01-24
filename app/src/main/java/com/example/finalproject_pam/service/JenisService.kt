package com.example.finalproject_pam.service


import com.example.finalproject_pam.model.AllJenisResponse
import com.example.finalproject_pam.model.Jenis
import com.example.finalproject_pam.model.JenisDetailResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface JenisService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    //@GET
    @GET("jenis")
    suspend fun getAllJenis(): AllJenisResponse

    //@GET
    @GET("jenis/{id_jenis}")
    suspend fun getJenisById(@Path("id_jenis")id_jenis: String): JenisDetailResponse

    //@POST
    @POST("jenis/jenis")
    suspend fun insertJenis(@Body jenis: Jenis)


    @PUT("jenis/{id_jenis}")
    suspend fun updateJenis(@Path("id_jenis")id_jenis: String, @Body jenis: Jenis)

    @DELETE("jenis/{id_jenis}")
    suspend fun deleteJenis(@Path("id_jenis")id_jenis: String):retrofit2.Response<Void>
}