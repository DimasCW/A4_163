package com.example.finalproject_pam.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalproject_pam.application.Applications
import com.example.finalproject_pam.ui.viewmodel.jenis.JenisDetailVM
import com.example.finalproject_pam.ui.viewmodel.jenis.JenisHomeVM
import com.example.finalproject_pam.ui.viewmodel.jenis.JenisInsertVM
import com.example.finalproject_pam.ui.viewmodel.jenis.JenisUpdateVM
import com.example.finalproject_pam.ui.viewmodel.manajer.ManajerDetailVM
import com.example.finalproject_pam.ui.viewmodel.manajer.ManajerHomeVM
import com.example.finalproject_pam.ui.viewmodel.manajer.ManajerInsertVM
import com.example.finalproject_pam.ui.viewmodel.manajer.ManajerUpdateVM
import com.example.finalproject_pam.ui.viewmodel.pemilik.PemilikDetailVM
import com.example.finalproject_pam.ui.viewmodel.pemilik.PemilikHomeVM
import com.example.finalproject_pam.ui.viewmodel.pemilik.PemilikInsertVM
import com.example.finalproject_pam.ui.viewmodel.pemilik.PemilikUpdateVM

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer { PemilikHomeVM(aplikasi().container.pemilikRepository) }
        initializer { PemilikInsertVM(aplikasi().container.pemilikRepository) }
        initializer { PemilikDetailVM(createSavedStateHandle(),aplikasi().container.pemilikRepository) }
        initializer { PemilikUpdateVM(createSavedStateHandle(),aplikasi().container.pemilikRepository) }

        initializer { JenisHomeVM(aplikasi().container.jenisRepository) }
        initializer { JenisInsertVM(aplikasi().container.jenisRepository) }
        initializer { JenisDetailVM(createSavedStateHandle(),aplikasi().container.jenisRepository) }
        initializer { JenisUpdateVM(createSavedStateHandle(),aplikasi().container.jenisRepository) }

        initializer { ManajerHomeVM(aplikasi().container.manajerRepository) }
        initializer { ManajerInsertVM(aplikasi().container.manajerRepository) }
        initializer { ManajerDetailVM(createSavedStateHandle(),aplikasi().container.manajerRepository) }
        initializer { ManajerUpdateVM(createSavedStateHandle(),aplikasi().container.manajerRepository) }
    }
    fun CreationExtras.aplikasi(): Applications =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as Applications)



}