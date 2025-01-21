package com.example.finalproject_pam.model

import kotlinx.serialization.Serializable

@Serializable
data class Pemilik(
    val id_pemilik: String,
    val nama_pemilik: String,
    val kontak_pemilik: String,
)
@Serializable
data class AllPemilikResponse(
    val status: Boolean,
    val message: String,
    val data: List<Pemilik>
)
@Serializable
data class PemilikDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Pemilik
)