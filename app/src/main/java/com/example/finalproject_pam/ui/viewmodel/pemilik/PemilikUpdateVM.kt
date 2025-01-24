package com.example.finalproject_pam.ui.viewmodel.pemilik

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject_pam.ui.view.pemilik.DestinasiUpdatePemilik
import com.example.finalproject_pam.repository.PemilikRepository
import kotlinx.coroutines.launch

class PemilikUpdateVM (
    savedStateHandle: SavedStateHandle,
    private val pmlk: PemilikRepository
): ViewModel(){
    var UpdateUiState by  mutableStateOf(PemilikInsertUiState())
        private set

    private val _id_pemilik: String = checkNotNull(savedStateHandle[DestinasiUpdatePemilik.ID_PEMILIK])

    init {
        viewModelScope.launch {
            UpdateUiState = pmlk.getPemilikById(_id_pemilik)
                .toUiStatePmlk()
        }
    }

    fun updateInsertPmlkState(pemilikinsertUiEvent: PemilikInsertUiEvent){
        UpdateUiState = PemilikInsertUiState(pemilikinsertUiEvent = pemilikinsertUiEvent)
    }

    suspend fun updatePmlk(){
        viewModelScope.launch {
            try {
                pmlk.updatePemilik(_id_pemilik, UpdateUiState.pemilikinsertUiEvent.toPmlk())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}