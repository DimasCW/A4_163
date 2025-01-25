package com.example.finalproject_pam.ui.viewmodel.manajer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject_pam.model.Manajer
import com.example.finalproject_pam.repository.ManajerRepository
import com.example.finalproject_pam.ui.view.manajer.DestinasiDetailManajer
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class ManajerDetailUiState {
    data class Success(val manajer: Manajer) : ManajerDetailUiState()
    object Error : ManajerDetailUiState()
    object Loading : ManajerDetailUiState()
}

class ManajerDetailVM(
    savedStateHandle: SavedStateHandle,
    private val mnjr: ManajerRepository
) : ViewModel() {

    var manajerDetailState: ManajerDetailUiState by mutableStateOf(ManajerDetailUiState.Loading)
        private set

    private val _id_manajer: String = checkNotNull(savedStateHandle[DestinasiDetailManajer.ID_MANAJER])

    init {
        getManajerById()
    }

    fun getManajerById() {
        viewModelScope.launch {
            manajerDetailState = ManajerDetailUiState.Loading
            manajerDetailState = try {
                val manajer = mnjr.getManajerById(_id_manajer)
                ManajerDetailUiState.Success(manajer)
            } catch (e: IOException) {
                ManajerDetailUiState.Error
            } catch (e: HttpException) {
                ManajerDetailUiState.Error
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
