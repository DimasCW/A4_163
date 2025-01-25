package com.example.finalproject_pam.ui.viewmodel.manajer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject_pam.repository.ManajerRepository
import com.example.finalproject_pam.ui.view.manajer.DestinasiUpdateManajer
import kotlinx.coroutines.launch

class ManajerUpdateVM (
    savedStateHandle: SavedStateHandle,
    private val mnjr: ManajerRepository
): ViewModel(){
    var UpdateUiState by  mutableStateOf(ManajerInsertUiState())
        private set

    private val _id_manajer: String = checkNotNull(savedStateHandle[DestinasiUpdateManajer.ID_MANAJER])

    init {
        viewModelScope.launch {
            UpdateUiState = mnjr.getManajerById(_id_manajer)
                .toUiStateMnjr()
        }
    }

    fun updateInsertMnjrState(manajerinsertUiEvent: ManajerInsertUiEvent){
        UpdateUiState = ManajerInsertUiState(manajerinsertUiEvent = manajerinsertUiEvent)
    }

    suspend fun updateMnjr(){
        viewModelScope.launch {
            try {
                mnjr.updateManajer(_id_manajer, UpdateUiState.manajerinsertUiEvent.toMnjr())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}