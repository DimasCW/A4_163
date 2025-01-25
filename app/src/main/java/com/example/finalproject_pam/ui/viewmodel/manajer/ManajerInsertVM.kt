package com.example.finalproject_pam.ui.viewmodel.manajer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject_pam.model.Manajer
import com.example.finalproject_pam.repository.ManajerRepository
import kotlinx.coroutines.launch


class ManajerInsertVM (private val mnjr: ManajerRepository): ViewModel() {
    var uiState by mutableStateOf(ManajerInsertUiState())
        private set

    fun updateInsertMnjrState(manajerinsertUiEvent:ManajerInsertUiEvent) {
        uiState = ManajerInsertUiState(manajerinsertUiEvent = manajerinsertUiEvent)
    }

    suspend fun insertMnjr() {
        viewModelScope.launch{
            try {
                mnjr.insertManajer(uiState.manajerinsertUiEvent.toMnjr())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class ManajerInsertUiState(
    val manajerinsertUiEvent: ManajerInsertUiEvent = ManajerInsertUiEvent()
)

data class ManajerInsertUiEvent(
    val id_manajer: String="",
    val nama_manajer: String="",
    val kontak_manajer: String=""
)

fun ManajerInsertUiEvent.toMnjr(): Manajer = Manajer(
    id_manajer = id_manajer,
    nama_manajer = nama_manajer,
    kontak_manajer = kontak_manajer

)

fun Manajer.toUiStateMnjr(): ManajerInsertUiState = ManajerInsertUiState(
    manajerinsertUiEvent = toManajerInsertUiEvent()
)

fun Manajer.toManajerInsertUiEvent(): ManajerInsertUiEvent = ManajerInsertUiEvent(
    id_manajer = id_manajer,
    nama_manajer = nama_manajer,
    kontak_manajer = kontak_manajer
)