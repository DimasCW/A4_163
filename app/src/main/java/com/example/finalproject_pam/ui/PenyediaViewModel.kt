package com.example.finalproject_pam.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalproject_pam.application.PemilikApplications
import com.example.finalproject_pam.ui.viewmodel.pemilik.PemilikDetailVM
import com.example.finalproject_pam.ui.viewmodel.pemilik.PemilikHomeVM
import com.example.finalproject_pam.ui.viewmodel.pemilik.PemilikInsertVM

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer { PemilikHomeVM(aplikasiPemilik().container.pemilikRepository) }
        initializer { PemilikInsertVM(aplikasiPemilik().container.pemilikRepository) }
        initializer { PemilikDetailVM(createSavedStateHandle(),aplikasiPemilik().container.pemilikRepository) }
//        initializer { UpdateViewModel(createSavedStateHandle(),aplikasiPemilik().container.pemilikRepository) }
    }
    fun CreationExtras.aplikasiPemilik(): PemilikApplications =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as PemilikApplications)
}