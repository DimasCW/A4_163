package com.example.finalproject_pam.ui.view.properti

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject_pam.application.Applications
import com.example.finalproject_pam.ui.PenyediaViewModel
import com.example.finalproject_pam.ui.costumwidget.CostumeTopAppBar
import com.example.finalproject_pam.ui.costumwidget.JenisDD
import com.example.finalproject_pam.ui.costumwidget.ManajerDD
import com.example.finalproject_pam.ui.costumwidget.PemilikDD
import com.example.finalproject_pam.ui.navigation.DestinasiNavigasi
import com.example.finalproject_pam.ui.viewmodel.properti.PropertiUpdateVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdateProperti: DestinasiNavigasi {
    override val route = "update_properti"
    override val titleRes = "Update Properti"
    const val ID_PROPERTI = "id_properti"
    val routesWithArg = "$route/{$ID_PROPERTI}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertiUpdateView(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: PropertiUpdateVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val context = LocalContext.current
    val aplikasi = context.applicationContext as Applications
    val pemilikRepository = aplikasi.container.pemilikRepository
    val manajerRepository = aplikasi.container.manajerRepository // Pastikan repository manajer diakses

    // Inisialisasi selectedPemilik, selectedJenis, dan selectedManajer
    var selectedPemilik by remember { mutableStateOf(viewModel.UpdateUiState.propertiinsertUiEvent.id_pemilik) }
    var selectedJenis by remember { mutableStateOf(viewModel.UpdateUiState.propertiinsertUiEvent.id_jenis) }
    var selectedManajer by remember { mutableStateOf(viewModel.UpdateUiState.propertiinsertUiEvent.id_manajer) }

    // Memuat data untuk dropdown
    LaunchedEffect(Unit) {
        PemilikDD.loadData(pemilikRepository)
        ManajerDD.loadData(manajerRepository) // Memuat data manajer
        // Perbarui nilai selectedPemilik, selectedJenis, dan selectedManajer setelah data dimuat
        selectedPemilik = viewModel.UpdateUiState.propertiinsertUiEvent.id_pemilik
        selectedJenis = viewModel.UpdateUiState.propertiinsertUiEvent.id_jenis
        selectedManajer = viewModel.UpdateUiState.propertiinsertUiEvent.id_manajer
    }

    // Sinkronisasi perubahan pada UpdateUiState
    LaunchedEffect(viewModel.UpdateUiState) {
        selectedPemilik = viewModel.UpdateUiState.propertiinsertUiEvent.id_pemilik
        selectedJenis = viewModel.UpdateUiState.propertiinsertUiEvent.id_jenis
        selectedManajer = viewModel.UpdateUiState.propertiinsertUiEvent.id_manajer
    }

    val coroutineScope = rememberCoroutineScope()


    // UI Scaffold dan komponen lainnya
    Scaffold(
        modifier = modifier,
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateProperti.titleRes,
                canNavigateBack = true,
                navigateUp = onBack,
            )
        }
    ) { padding ->
        EntryBody(
            modifier = Modifier.padding(padding),
            propertiinsertUiState = viewModel.UpdateUiState,
            onPropertiValueChange = viewModel::updateInsertPrptState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePrpt()
                    delay(600)
                    withContext(Dispatchers.Main) {
                        onNavigate()
                    }
                }
            },
            optionsPemilik = PemilikDD.optionsPemilik.collectAsState().value,
            optionsJenis = JenisDD.optionsJenis.collectAsState().value,
            optionsManajer = ManajerDD.optionsManajer.collectAsState().value, // Pastikan optionsManajer terhubung dengan data manajer
            selectedPemilik = selectedPemilik,
            selectedJenis = selectedJenis,
            selectedManajer = selectedManajer,
            onSelectedPemilikChange = { selectedPemilik = it },
            onSelectedJenisChange = { selectedJenis = it },
            onSelectedManajerChange = { selectedManajer = it },
        )
    }
}