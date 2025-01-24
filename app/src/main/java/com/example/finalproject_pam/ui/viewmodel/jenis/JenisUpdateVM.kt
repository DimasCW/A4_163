package com.example.finalproject_pam.ui.viewmodel.jenis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject_pam.repository.JenisRepository
import com.example.finalproject_pam.ui.view.jenis.DestinasiUpdateJenis
import kotlinx.coroutines.launch

class JenisUpdateVM (
    savedStateHandle: SavedStateHandle,
    private val jns: JenisRepository
): ViewModel(){
    var UpdateUiState by  mutableStateOf(JenisInsertUiState())
        private set

    private val _id_jenis: String = checkNotNull(savedStateHandle[DestinasiUpdateJenis.ID_Jenis])

    init {
        viewModelScope.launch {
            UpdateUiState = jns.getJenisById(_id_jenis)
                .toUiStateJns()
        }
    }

    fun updateInsertJnsState(jenisinsertUiEvent: JenisInsertUiEvent){
        UpdateUiState = JenisInsertUiState(jenisinsertUiEvent = jenisinsertUiEvent)
    }

    suspend fun updateJns(){
        viewModelScope.launch {
            try {
                jns.updateJenis(_id_jenis, UpdateUiState.jenisinsertUiEvent.toJns())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}