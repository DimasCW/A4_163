package com.example.finalproject_pam.model

import kotlinx.serialization.Serializable

@Serializable
data class Manajer(
    val id_manajer: String,
    val nama_manajer: String,
    val kontak_manajer: String,
)
@Serializable
data class AllManajerResponse(
    val status: Boolean,
    val message: String,
    val data: List<Manajer>
)
@Serializable
data class ManajerDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Manajer
)