package com.example.finalproject_pam.model

data class Pemilik(
    val id_pemilik: String,
    val nama_pemilik: String,
    val kontak_pemilik: String,
)

data class AllPemilikResponse(
    val status: Boolean,
    val message: String,
    val data: List<Pemilik>
)

data class PemilikDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Pemilik
)