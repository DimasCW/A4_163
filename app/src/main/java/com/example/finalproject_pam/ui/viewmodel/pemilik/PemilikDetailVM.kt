package com.example.finalproject_pam.ui.viewmodel.pemilik

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject_pam.model.Pemilik
import com.example.finalproject_pam.repository.PemilikRepository
import com.example.finalproject_pam.ui.view.pemilik.DestinasiDetail
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class PemilikDetailUiState {
    data class Success(val pemilik: Pemilik) : PemilikDetailUiState()
    object Error : PemilikDetailUiState()
    object Loading : PemilikDetailUiState()
}

class PemilikDetailVM(
    savedStateHandle: SavedStateHandle,
    private val pmlk: PemilikRepository
) : ViewModel() {

    var pemilikDetailState: PemilikDetailUiState by mutableStateOf(PemilikDetailUiState.Loading)
        private set

    private val _id_pemilik: String = checkNotNull(savedStateHandle[DestinasiDetail.ID_PEMILIK])

    init {
        getPemilikById()
    }

    fun getPemilikById() {
        viewModelScope.launch {
            pemilikDetailState = PemilikDetailUiState.Loading
            pemilikDetailState = try {
                val pemilik = pmlk.getPemilikById(_id_pemilik)
                PemilikDetailUiState.Success(pemilik)
            } catch (e: IOException) {
                PemilikDetailUiState.Error
            } catch (e: HttpException) {
                PemilikDetailUiState.Error
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
