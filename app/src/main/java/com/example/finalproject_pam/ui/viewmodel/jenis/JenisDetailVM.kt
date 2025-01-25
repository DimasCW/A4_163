package com.example.finalproject_pam.ui.viewmodel.jenis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject_pam.model.Jenis
import com.example.finalproject_pam.repository.JenisRepository
import com.example.finalproject_pam.ui.view.jenis.DestinasiDetailJenis
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class JenisDetailUiState {
    data class Success(val jenis: Jenis) : JenisDetailUiState()
    object Error : JenisDetailUiState()
    object Loading : JenisDetailUiState()
}

class JenisDetailVM(
    savedStateHandle: SavedStateHandle,
    private val jns: JenisRepository
) : ViewModel() {

    var jenisDetailState: JenisDetailUiState by mutableStateOf(JenisDetailUiState.Loading)
        private set

    private val _id_jenis: String = checkNotNull(savedStateHandle[DestinasiDetailJenis.ID_JENIS])

    init {
        getJenisById()
    }

    fun getJenisById() {
        viewModelScope.launch {
            jenisDetailState = JenisDetailUiState.Loading
            jenisDetailState = try {
                val jenis = jns.getJenisById(_id_jenis)
                JenisDetailUiState.Success(jenis)
            } catch (e: IOException) {
                JenisDetailUiState.Error
            } catch (e: HttpException) {
                JenisDetailUiState.Error
            }
        }
    }

    fun deleteJenis(id_jenis:String) {
        viewModelScope.launch {
            try {
                jns.deleteJenis(id_jenis)
            }catch (e: IOException){
                JenisHomeUiState.Error
            }catch (e: HttpException){
                JenisHomeUiState.Error
            }
        }
    }
}
