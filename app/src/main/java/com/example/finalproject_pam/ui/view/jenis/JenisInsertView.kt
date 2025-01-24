package com.example.finalproject_pam.ui.view.jenis

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject_pam.ui.PenyediaViewModel
import com.example.finalproject_pam.ui.costumwidget.CostumeTopAppBar
import com.example.finalproject_pam.ui.navigation.DestinasiNavigasi
import com.example.finalproject_pam.ui.viewmodel.jenis.JenisInsertUiEvent
import com.example.finalproject_pam.ui.viewmodel.jenis.JenisInsertUiState
import com.example.finalproject_pam.ui.viewmodel.jenis.JenisInsertVM
import kotlinx.coroutines.launch


object DestinasiEntryJenis: DestinasiNavigasi {
    override val route ="entry_jenis"
    override val titleRes = "Insert Jenis"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryJnsScreen(
    navigateBack: ()-> Unit,
    modifier: Modifier = Modifier,
    viewModel: JenisInsertVM = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier=modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryJenis.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){ innerPadding ->
        EntryBody(
            jenisinsertUiState = viewModel.uiState,
            onJenisValueChange = viewModel::updateInsertJnsState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertJns()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBody(
    jenisinsertUiState: JenisInsertUiState,
    onJenisValueChange: (JenisInsertUiEvent)->Unit,
    onSaveClick: ()->Unit,
    modifier: Modifier = Modifier
){
    Column (
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInput(
            jenisinsertUiEvent = jenisinsertUiState.jenisinsertUiEvent,
            onValueChange = onJenisValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    jenisinsertUiEvent: JenisInsertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (JenisInsertUiEvent)->Unit={},
    enabled: Boolean = true
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        OutlinedTextField(
            value = jenisinsertUiEvent.id_jenis,
            onValueChange = {onValueChange(jenisinsertUiEvent.copy(id_jenis = it))},
            label = { Text("Id jenis") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = jenisinsertUiEvent.nama_jenis,
            onValueChange = {onValueChange(jenisinsertUiEvent.copy(nama_jenis = it))},
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = jenisinsertUiEvent.deskripsi_jenis,
            onValueChange = {onValueChange(jenisinsertUiEvent.copy(deskripsi_jenis = it))},
            label = { Text("Kontak ") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )


        if (enabled){
            Text(
                text = "Isi Semua Data",
                modifier = Modifier.padding(12.dp)
            )
        }
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}