package com.example.finalproject_pam.ui.viewmodel.jenis


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject_pam.model.Jenis
import com.example.finalproject_pam.repository.JenisRepository
import kotlinx.coroutines.launch

class JenisInsertVM (private val jns: JenisRepository): ViewModel() {
    var uiState by mutableStateOf(JenisInsertUiState())
        private set

    fun updateInsertJnsState(jenisinsertUiEvent:JenisInsertUiEvent) {
        uiState = JenisInsertUiState(jenisinsertUiEvent = jenisinsertUiEvent)
    }

    suspend fun insertJns() {
        viewModelScope.launch{
            try {
                jns.insertJenis(uiState.jenisinsertUiEvent.toJns())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class JenisInsertUiState(
    val jenisinsertUiEvent: JenisInsertUiEvent = JenisInsertUiEvent()
)

data class JenisInsertUiEvent(
    val id_jenis: String="",
    val nama_jenis: String="",
    val deskripsi_jenis: String=""
)

fun JenisInsertUiEvent.toJns(): Jenis = Jenis(
    id_jenis = id_jenis,
    nama_jenis = nama_jenis,
    deskripsi_jenis = deskripsi_jenis

)

fun Jenis.toUiStateJns(): JenisInsertUiState = JenisInsertUiState(
    jenisinsertUiEvent = toJenisInsertUiEvent()
)

fun Jenis.toJenisInsertUiEvent(): JenisInsertUiEvent = JenisInsertUiEvent(
    id_jenis = id_jenis,
    nama_jenis = nama_jenis,
    deskripsi_jenis = deskripsi_jenis
)