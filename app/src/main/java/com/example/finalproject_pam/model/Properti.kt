package com.example.finalproject_pam.model

import kotlinx.serialization.Serializable

@Serializable
data class Properti(
    val id_properti: String,
    val nama_properti: String,
    val deskripsi_properti: String,
    val lokasi : String,
    val harga : String,
    val status_properti : String,
    val id_jenis : String,
    val id_pemilik : String,
    val id_manajer : String,
)
@Serializable
data class AllPropertiResponse(
    val status: Boolean,
    val message: String,
    val data: List<Properti>
)
@Serializable
data class PropertiDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Properti
)