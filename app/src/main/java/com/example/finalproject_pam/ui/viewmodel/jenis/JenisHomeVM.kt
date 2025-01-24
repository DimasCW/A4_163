package com.example.finalproject_pam.ui.viewmodel.jenis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject_pam.model.Jenis
import com.example.finalproject_pam.repository.JenisRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class JenisHomeUiState{
    data class Success(val jenis: List<Jenis>):JenisHomeUiState()
    object Error:JenisHomeUiState()
    object Loading:JenisHomeUiState()
}

class JenisHomeVM (private val jns: JenisRepository): ViewModel() {
    var jnsUIState: JenisHomeUiState by mutableStateOf(JenisHomeUiState.Loading)
        private set

    init {
        getJns()
    }

    fun getJns(){
        viewModelScope.launch {
            jnsUIState=JenisHomeUiState.Loading
            jnsUIState=try {
                JenisHomeUiState.Success(jns.getJenis().data)
            }catch (e: IOException){
                JenisHomeUiState.Error
            }catch (e: HttpException){
                JenisHomeUiState.Error
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
