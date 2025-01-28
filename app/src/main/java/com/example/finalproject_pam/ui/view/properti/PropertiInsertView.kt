package com.example.finalproject_pam.ui.view.properti


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject_pam.ui.PenyediaViewModel
import com.example.finalproject_pam.ui.costumwidget.CostumeTopAppBar
import com.example.finalproject_pam.ui.costumwidget.DynamicSelectTextField
import com.example.finalproject_pam.ui.costumwidget.PemilikDD
import com.example.finalproject_pam.ui.navigation.DestinasiNavigasi
import com.example.finalproject_pam.ui.viewmodel.properti.PropertiInsertUiEvent
import com.example.finalproject_pam.ui.viewmodel.properti.PropertiInsertUiState
import com.example.finalproject_pam.ui.viewmodel.properti.PropertiInsertVM
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.finalproject_pam.ui.costumwidget.JenisDD
import com.example.finalproject_pam.ui.costumwidget.ManajerDD

import kotlinx.coroutines.launch


object DestinasiEntryProperti: DestinasiNavigasi {
    override val route ="entry_properti"
    override val titleRes = "Insert Properti"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPrptScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PropertiInsertVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val context = LocalContext.current
    val aplikasi = context.applicationContext as com.example.finalproject_pam.application.Applications
    val pemilikRepository = aplikasi.container.pemilikRepository
    val jenisRepository = aplikasi.container.jenisRepository
    val manajerRepository = aplikasi.container.manajerRepository

    LaunchedEffect(Unit) {
        PemilikDD.loadData(pemilikRepository)
        JenisDD.loadData(jenisRepository)
        ManajerDD.loadData(manajerRepository)
    }

    val optionsPemilik by PemilikDD.optionsPemilik.collectAsState(initial = emptyList())
    val optionsJenis by JenisDD.optionsJenis.collectAsState(initial = emptyList())
    val optionsManajer by ManajerDD.optionsManajer.collectAsState(initial = emptyList())

    var selectedPemilik by remember { mutableStateOf("") }
    var selectedJenis by remember { mutableStateOf("") }
    var selectedManajer by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryProperti.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBody(
            optionsPemilik = optionsPemilik,
            optionsJenis = optionsJenis,
            optionsManajer = optionsManajer,
            selectedPemilik = selectedPemilik,
            onSelectedPemilikChange = { selectedPemilik = it },
            selectedJenis = selectedJenis,
            onSelectedJenisChange = { selectedJenis = it },
            selectedManajer = selectedManajer,
            onSelectedManajerChange = { selectedManajer = it },
            propertiinsertUiState = viewModel.uiState,
            onPropertiValueChange = viewModel::updateInsertPrptState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPrpt()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        )
    }
}

@Composable
fun EntryBody(
    optionsPemilik: List<String>,
    optionsJenis: List<String>,
    optionsManajer: List<String>,
    selectedPemilik: String,
    selectedJenis: String,
    selectedManajer: String,
    onSelectedPemilikChange: (String) -> Unit,
    onSelectedJenisChange: (String) -> Unit,
    onSelectedManajerChange: (String) -> Unit,
    propertiinsertUiState: PropertiInsertUiState,
    onPropertiValueChange: (PropertiInsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = modifier
    ) {


        FormInput(
            optionsPemilik = optionsPemilik,
            optionsJenis = optionsJenis,
            optionsManajer = optionsManajer,
            selectedPemilik = selectedPemilik,
            onSelectedPemilikChange = onSelectedPemilikChange,
            selectedJenis = selectedJenis,
            onSelectedJenisChange = onSelectedJenisChange,
            selectedManajer = selectedManajer,
            onSelectedManajerChange = onSelectedManajerChange,
            propertiinsertUiEvent = propertiinsertUiState.propertiinsertUiEvent,
            onValueChange = onPropertiValueChange,
            onSave = onSaveClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )
    }
}

@Composable
fun FormInput(
    optionsPemilik: List<String>,
    optionsJenis: List<String>,
    optionsManajer: List<String>,
    selectedPemilik: String,
    onSelectedPemilikChange: (String) -> Unit,
    selectedJenis: String,
    onSelectedJenisChange: (String) -> Unit,
    selectedManajer: String,
    onSelectedManajerChange: (String) -> Unit,
    propertiinsertUiEvent: PropertiInsertUiEvent,
    onValueChange: (PropertiInsertUiEvent) -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val statusOptions = listOf("Tersedia", "Tersewa", "Dijual")

    val isFormValid = propertiinsertUiEvent.id_properti.isNotBlank() &&
            propertiinsertUiEvent.nama_properti.isNotBlank() &&
            propertiinsertUiEvent.deskripsi_properti.isNotBlank() &&
            propertiinsertUiEvent.lokasi.isNotBlank() &&
            propertiinsertUiEvent.harga.isNotBlank() &&
            propertiinsertUiEvent.status_properti.isNotBlank() &&
            selectedJenis.isNotBlank() &&
            selectedPemilik.isNotBlank() &&
            selectedManajer.isNotBlank()

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        OutlinedTextField(
            value = propertiinsertUiEvent.id_properti,
            onValueChange = {
                if (it.length <= 15) {
                    onValueChange(propertiinsertUiEvent.copy(id_properti = it))
                }
            },
            label = { Text("Id Properti", style = MaterialTheme.typography.bodyMedium) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF6200EE), // Warna ungu modern
                unfocusedBorderColor = Color.Gray
            )
        )

        OutlinedTextField(
            value = propertiinsertUiEvent.nama_properti,
            onValueChange = {
                if (it.length <= 100) {
                    onValueChange(propertiinsertUiEvent.copy(nama_properti = it))
                }
            },
            label = { Text("Nama Properti", style = MaterialTheme.typography.bodyMedium) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF6200EE), // Warna ungu modern
                unfocusedBorderColor = Color.Gray
            )
        )

        OutlinedTextField(
            value = propertiinsertUiEvent.deskripsi_properti,
            onValueChange = {
                if (it.length <= 500) {
                    onValueChange(propertiinsertUiEvent.copy(deskripsi_properti = it))
                }
            },
            label = { Text("Deskripsi Properti", style = MaterialTheme.typography.bodyMedium) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF6200EE), // Warna ungu modern
                unfocusedBorderColor = Color.Gray
            )
        )

        OutlinedTextField(
            value = propertiinsertUiEvent.lokasi,
            onValueChange = {
                if (it.length <= 255) {
                    onValueChange(propertiinsertUiEvent.copy(lokasi = it))
                }
            },
            label = { Text("Lokasi", style = MaterialTheme.typography.bodyMedium) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF6200EE), // Warna ungu modern
                unfocusedBorderColor = Color.Gray
            )
        )

        OutlinedTextField(
            value = propertiinsertUiEvent.harga,
            onValueChange = {
                if (it.length <= 20) {
                    onValueChange(propertiinsertUiEvent.copy(harga = it))
                }
            },
            label = { Text("Harga", style = MaterialTheme.typography.bodyMedium) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF6200EE), // Warna ungu modern
                unfocusedBorderColor = Color.Gray
            )
        )

        DynamicSelectTextField(
            selectedValue = propertiinsertUiEvent.status_properti,
            options = statusOptions,
            label = "Status Properti",
            onValueChangedEvent = { value ->
                if (value.length <= 10) onValueChange(propertiinsertUiEvent.copy(status_properti = value))
            },
            modifier = Modifier.fillMaxWidth()
        )

        DynamicSelectTextField(
            selectedValue = selectedJenis,
            options = optionsJenis,
            label = "ID Jenis",
            onValueChangedEvent = { value ->
                onSelectedJenisChange(value)
            },
            modifier = Modifier.fillMaxWidth()
        )

        DynamicSelectTextField(
            selectedValue = selectedPemilik,
            options = optionsPemilik,
            label = "ID Pemilik",
            onValueChangedEvent = { value ->
                onSelectedPemilikChange(value)
            },
            modifier = Modifier.fillMaxWidth()
        )

        DynamicSelectTextField(
            selectedValue = selectedManajer,
            options = optionsManajer,
            label = "ID Manajer",
            onValueChangedEvent = { value ->
                onSelectedManajerChange(value)
            },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { if (isFormValid) onSave() },
            enabled = isFormValid,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6200EE), // Warna ungu modern
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Simpan", style = MaterialTheme.typography.titleMedium)
        }
    }
}