package com.example.finalproject_pam.ui.view.pemilik

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
import com.example.finalproject_pam.ui.viewmodel.pemilik.PemilikInsertUiEvent
import com.example.finalproject_pam.ui.viewmodel.pemilik.PemilikInsertUiState
import com.example.finalproject_pam.ui.viewmodel.pemilik.PemilikInsertVM
import kotlinx.coroutines.launch


object DestinasiEntryPemilik: DestinasiNavigasi {
    override val route ="entry_pemilik"
    override val titleRes = "Insert Pemilik"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPmlkScreen(
    navigateBack: ()-> Unit,
    modifier: Modifier = Modifier,
    viewModel: PemilikInsertVM = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier=modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryPemilik.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){ innerPadding ->
        EntryBody(
            pemilikinsertUiState = viewModel.uiState,
            onPemilikValueChange = viewModel::updateInsertPmlkState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPmlk()
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
    pemilikinsertUiState: PemilikInsertUiState,
    onPemilikValueChange: (PemilikInsertUiEvent)->Unit,
    onSaveClick: ()->Unit,
    modifier: Modifier = Modifier
){
    Column (
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInput(
            pemilikinsertUiEvent = pemilikinsertUiState.pemilikinsertUiEvent,
            onValueChange = onPemilikValueChange,
            modifier = Modifier.fillMaxWidth(),
            onSave = onSaveClick
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    pemilikinsertUiEvent: PemilikInsertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (PemilikInsertUiEvent)->Unit={},
    enabled: Boolean = true,
    onSave: ()->Unit = {},
) {

    val isFormValid = pemilikinsertUiEvent.id_pemilik.isNotBlank() &&
            pemilikinsertUiEvent.nama_pemilik.isNotBlank() &&
            pemilikinsertUiEvent.kontak_pemilik.isNotBlank()
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        OutlinedTextField(
            value = pemilikinsertUiEvent.id_pemilik,
            onValueChange = {
                if (it.length <= 15) {
                    onValueChange(pemilikinsertUiEvent.copy(id_pemilik = it))
                }
            },
            label = { Text("Id Pemilik") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = pemilikinsertUiEvent.nama_pemilik,
            onValueChange = {
                if (it.length <= 30) {
                    onValueChange(pemilikinsertUiEvent.copy(nama_pemilik = it))
                }
            },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = pemilikinsertUiEvent.kontak_pemilik,
            onValueChange = {
                if (it.length <= 15) {
                    onValueChange(pemilikinsertUiEvent.copy(kontak_pemilik = it))
                }
            },
            label = { Text("Kontak ") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )


        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )

        Button(
            onClick = { if (isFormValid) onSave() },
            enabled = isFormValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan")
        }
    }
}