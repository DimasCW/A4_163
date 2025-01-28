package com.example.finalproject_pam.ui.viewmodel.properti

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject_pam.model.Properti
import com.example.finalproject_pam.repository.PropertiRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException


sealed class PropertiHomeUiState{
    data class Success(val properti: List<Properti>):PropertiHomeUiState()
    object Error:PropertiHomeUiState()
    object Loading:PropertiHomeUiState()
}

class PropertiHomeVM (private val prpt: PropertiRepository): ViewModel() {
    var prptUIState: PropertiHomeUiState by mutableStateOf(PropertiHomeUiState.Loading)
        private set

    init {
        getPrpt()
    }

    fun getPrpt(){
        viewModelScope.launch {
            prptUIState=PropertiHomeUiState.Loading
            prptUIState=try {
                PropertiHomeUiState.Success(prpt.getProperti().data)
            }catch (e: IOException){
                PropertiHomeUiState.Error
            }catch (e: HttpException){
                PropertiHomeUiState.Error
            }
        }
    }

    fun deleteProperti(id_properti:String) {
        viewModelScope.launch {
            try {
                prpt.deleteProperti(id_properti)
            }catch (e: IOException){
                PropertiHomeUiState.Error
            }catch (e: HttpException){
                PropertiHomeUiState.Error
            }
        }
    }
}

