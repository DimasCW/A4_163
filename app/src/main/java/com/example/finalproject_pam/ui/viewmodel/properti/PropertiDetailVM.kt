package com.example.finalproject_pam.ui.viewmodel.properti

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject_pam.model.Properti
import com.example.finalproject_pam.repository.PropertiRepository
import com.example.finalproject_pam.ui.view.properti.DestinasiDetailProperti
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class PropertiDetailUiState {
    data class Success(val properti: Properti) : PropertiDetailUiState()
    object Error : PropertiDetailUiState()
    object Loading : PropertiDetailUiState()
}

class PropertiDetailVM(
    savedStateHandle: SavedStateHandle,
    private val prpt: PropertiRepository
) : ViewModel() {

    var propertiDetailState: PropertiDetailUiState by mutableStateOf(PropertiDetailUiState.Loading)
        private set

    private val _id_properti: String = checkNotNull(savedStateHandle[DestinasiDetailProperti.ID_PROPERTI])

    init {
        getPropertiById()
    }

    fun getPropertiById() {
        viewModelScope.launch {
            propertiDetailState = PropertiDetailUiState.Loading
            propertiDetailState = try {
                val properti = prpt.getPropertiById(_id_properti)
                PropertiDetailUiState.Success(properti)
            } catch (e: IOException) {
                PropertiDetailUiState.Error
            } catch (e: HttpException) {
                PropertiDetailUiState.Error
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