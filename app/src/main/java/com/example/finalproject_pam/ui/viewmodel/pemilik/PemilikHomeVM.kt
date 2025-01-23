package com.example.finalproject_pam.ui.viewmodel.pemilik

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject_pam.model.Pemilik
import com.example.session12.repository.PemilikRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class PemilikHomeUiState{
    data class Success(val pemilik: List<Pemilik>):PemilikHomeUiState()
    object Error:PemilikHomeUiState()
    object Loading:PemilikHomeUiState()
}

class PemilikHomeVM (private val pmlk: PemilikRepository): ViewModel() {
    var pmlkUIState: PemilikHomeUiState by mutableStateOf(PemilikHomeUiState.Loading)
        private set

    init {
        getPmlk()
    }

    fun getPmlk(){
        viewModelScope.launch {
            pmlkUIState=PemilikHomeUiState.Loading
            pmlkUIState=try {
                PemilikHomeUiState.Success(pmlk.getPemilik().data)
            }catch (e: IOException){
                PemilikHomeUiState.Error
            }catch (e: HttpException){
                PemilikHomeUiState.Error
            }
        }
    }

    fun deletePemilik(id_pemilik:String) {
        viewModelScope.launch {
            try {
                pmlk.deletePemilik(id_pemilik)
            }catch (e: IOException){
                PemilikHomeUiState.Error
            }catch (e: HttpException){
                PemilikHomeUiState.Error
            }
        }
    }
}

