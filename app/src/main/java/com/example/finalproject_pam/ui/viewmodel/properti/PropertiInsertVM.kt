package com.example.finalproject_pam.ui.viewmodel.properti

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject_pam.model.Properti
import com.example.finalproject_pam.repository.PropertiRepository
import com.example.finalproject_pam.ui.viewmodel.pemilik.PemilikHomeUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class PropertiInsertVM (private val prpt: PropertiRepository): ViewModel() {
    var uiState by mutableStateOf(PropertiInsertUiState())
        private set

    fun updateInsertPrptState(propertiinsertUiEvent:PropertiInsertUiEvent) {
        uiState = PropertiInsertUiState(propertiinsertUiEvent = propertiinsertUiEvent)
    }


    suspend fun insertPrpt() {
        viewModelScope.launch{
            try {
                prpt.insertProperti(uiState.propertiinsertUiEvent.toPrpt())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}


data class PropertiInsertUiState(
    val propertiinsertUiEvent: PropertiInsertUiEvent = PropertiInsertUiEvent()
)

data class PropertiInsertUiEvent(
    val id_properti: String = "",
    val nama_properti: String = "",
    val deskripsi_properti: String = "",
    val lokasi : String = "",
    val harga : String = "",
    val status_properti : String = "",
    val id_jenis : String = "",
    val id_pemilik : String = "",
    val id_manajer : String = "",
)

fun PropertiInsertUiEvent.toPrpt(): Properti = Properti(
    id_properti = id_properti,
    nama_properti = nama_properti,
    deskripsi_properti = deskripsi_properti,
    lokasi = lokasi,
    harga = harga,
    status_properti = status_properti,
    id_jenis = id_jenis,
    id_pemilik = id_pemilik,
    id_manajer = id_manajer
)

fun Properti.toUiStatePrpt(): PropertiInsertUiState = PropertiInsertUiState(
    propertiinsertUiEvent = toPropertiInsertUiEvent()
)

fun Properti.toPropertiInsertUiEvent(): PropertiInsertUiEvent = PropertiInsertUiEvent(
    id_properti = id_properti,
    nama_properti = nama_properti,
    deskripsi_properti = deskripsi_properti,
    lokasi = lokasi,
    harga = harga,
    status_properti = status_properti,
    id_jenis = id_jenis,
    id_pemilik = id_pemilik,
    id_manajer = id_manajer
)