package com.example.finalproject_pam.ui.viewmodel.properti

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject_pam.repository.PropertiRepository
import kotlinx.coroutines.launch
import com.example.finalproject_pam.ui.view.properti.DestinasiUpdateProperti


class PropertiUpdateVM (
    savedStateHandle: SavedStateHandle,
    private val prpt: PropertiRepository
): ViewModel(){
    var UpdateUiState by  mutableStateOf(PropertiInsertUiState())
        private set



    private val _id_properti: String = checkNotNull(savedStateHandle[DestinasiUpdateProperti.ID_PROPERTI])

    init {
        viewModelScope.launch {
            UpdateUiState = prpt.getPropertiById(_id_properti)
                .toUiStatePrpt()
        }
    }

    fun updateInsertPrptState(propertiinsertUiEvent: PropertiInsertUiEvent){
        UpdateUiState = PropertiInsertUiState(propertiinsertUiEvent = propertiinsertUiEvent)
    }

    suspend fun updatePrpt(){
        viewModelScope.launch {
            try {
                prpt.updateProperti(_id_properti, UpdateUiState.propertiinsertUiEvent.toPrpt())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}