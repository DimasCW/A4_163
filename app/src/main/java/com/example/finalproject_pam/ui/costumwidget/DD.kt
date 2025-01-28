package com.example.finalproject_pam.ui.costumwidget

import com.example.finalproject_pam.repository.JenisRepository
import com.example.finalproject_pam.repository.ManajerRepository
import com.example.finalproject_pam.repository.PemilikRepository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

object PemilikDD {
    private val _optionsPemilik = MutableStateFlow<List<String>>(emptyList()) // List dengan format "Id_aset: Nama_aset"
    val optionsPemilik: StateFlow<List<String>> = _optionsPemilik

    // Fungsi untuk memuat data dari server
    fun loadData(PemilikRepository: PemilikRepository) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Memanggil data aset dari repository
                val response = PemilikRepository.getPemilik()
                if (response.status) {
                    // Mengubah data menjadi format "Id_aset: Nama_aset"
                    _optionsPemilik.value = response.data.map { aset ->
                        "${aset.nama_pemilik}"
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
object JenisDD {
    private val _optionsJenis = MutableStateFlow<List<String>>(emptyList()) // List dengan format "Id_aset: Nama_aset"
    val optionsJenis: StateFlow<List<String>> = _optionsJenis

    // Fungsi untuk memuat data dari server
    fun loadData(JenisRepository: JenisRepository) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Memanggil data aset dari repository
                val response = JenisRepository.getJenis()
                if (response.status) {
                    // Mengubah data menjadi format "Id_aset: Nama_aset"
                    _optionsJenis.value = response.data.map { jenis ->
                        "${jenis.nama_jenis}"
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
object ManajerDD {
    private val _optionsManajer = MutableStateFlow<List<String>>(emptyList()) // List dengan format "Id_aset: Nama_aset"
    val optionsManajer: StateFlow<List<String>> = _optionsManajer

    // Fungsi untuk memuat data dari server
    fun loadData(ManajerRepository: ManajerRepository) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Memanggil data aset dari repository
                val response = ManajerRepository.getManajer()
                if (response.status) {
                    // Mengubah data menjadi format "Id_aset: Nama_aset"
                    _optionsManajer.value = response.data.map { manajer ->
                        "${manajer.nama_manajer}"
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}