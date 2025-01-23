package com.example.finalproject_pam.ui.viewmodel.pemilik

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject_pam.model.Pemilik
import com.example.session12.repository.PemilikRepository
import kotlinx.coroutines.launch

class PemilikInsertVM (private val pmlk: PemilikRepository): ViewModel() {
    var uiState by mutableStateOf(PemilikInsertUiState())
        private set

    fun updateInsertPmlkState(pemilikinsertUiEvent:PemilikInsertUiEvent) {
        uiState = PemilikInsertUiState(pemilikinsertUiEvent = pemilikinsertUiEvent)
    }

    suspend fun insertPemilik() {
        viewModelScope.launch{
            try {
                pmlk.insertPemilik(uiState.pemilikinsertUiEvent.toPmlk())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class PemilikInsertUiState(
    val pemilikinsertUiEvent: PemilikInsertUiEvent = PemilikInsertUiEvent()
)

data class PemilikInsertUiEvent(
    val id_pemilik: String="",
    val nama_pemilik: String="",
    val kontak_pemilik: String=""
)

fun PemilikInsertUiEvent.toPmlk(): Pemilik = Pemilik(
    id_pemilik = id_pemilik,
    nama_pemilik = nama_pemilik,
    kontak_pemilik = kontak_pemilik

)

fun Pemilik.toUiStatePmlk(): PemilikInsertUiState = PemilikInsertUiState(
    pemilikinsertUiEvent = toPemilikInsertUiEvent()
)

fun Pemilik.toPemilikInsertUiEvent(): PemilikInsertUiEvent = PemilikInsertUiEvent(
    id_pemilik = id_pemilik,
    nama_pemilik = nama_pemilik,
    kontak_pemilik = kontak_pemilik
)