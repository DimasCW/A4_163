package com.example.finalproject_pam.ui.viewmodel.manajer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject_pam.model.Manajer
import com.example.finalproject_pam.repository.ManajerRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException


sealed class ManajerHomeUiState{
    data class Success(val manajer: List<Manajer>):ManajerHomeUiState()
    object Error:ManajerHomeUiState()
    object Loading:ManajerHomeUiState()
}

class ManajerHomeVM (private val mnjr: ManajerRepository): ViewModel() {
    var mnjrUIState: ManajerHomeUiState by mutableStateOf(ManajerHomeUiState.Loading)
        private set

    init {
        getMnjr()
    }

    fun getMnjr(){
        viewModelScope.launch {
            mnjrUIState=ManajerHomeUiState.Loading
            mnjrUIState=try {
                ManajerHomeUiState.Success(mnjr.getManajer().data)
            }catch (e: IOException){
                ManajerHomeUiState.Error
            }catch (e: HttpException){
                ManajerHomeUiState.Error
            }
        }
    }

    fun deleteManajer(id_manajer:String) {
        viewModelScope.launch {
            try {
                mnjr.deleteManajer(id_manajer)
            }catch (e: IOException){
                ManajerHomeUiState.Error
            }catch (e: HttpException){
                ManajerHomeUiState.Error
            }
        }
    }
}
