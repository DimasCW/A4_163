package com.example.finalproject_pam.ui.view.manajer

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
import com.example.finalproject_pam.ui.viewmodel.manajer.ManajerInsertUiEvent
import com.example.finalproject_pam.ui.viewmodel.manajer.ManajerInsertUiState
import com.example.finalproject_pam.ui.viewmodel.manajer.ManajerInsertVM
import kotlinx.coroutines.launch


object DestinasiEntryManajer: DestinasiNavigasi {
    override val route ="entry_manajer"
    override val titleRes = "Insert Manajer"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryMnjrScreen(
    navigateBack: ()-> Unit,
    modifier: Modifier = Modifier,
    viewModel: ManajerInsertVM = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier=modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryManajer.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){ innerPadding ->
        EntryBody(
            manajerinsertUiState = viewModel.uiState,
            onManajerValueChange = viewModel::updateInsertMnjrState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertMnjr()
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
    manajerinsertUiState: ManajerInsertUiState,
    onManajerValueChange: (ManajerInsertUiEvent)->Unit,
    onSaveClick: ()->Unit,
    modifier: Modifier = Modifier
){
    Column (
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInput(
            manajerinsertUiEvent = manajerinsertUiState.manajerinsertUiEvent,
            onValueChange = onManajerValueChange,
            modifier = Modifier.fillMaxWidth(),
            onSave = onSaveClick
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    manajerinsertUiEvent: ManajerInsertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (ManajerInsertUiEvent)->Unit={},
    enabled: Boolean = true,
    onSave: ()->Unit = {}
) {
    val isFormValid = manajerinsertUiEvent.id_manajer.isNotBlank() &&
            manajerinsertUiEvent.nama_manajer.isNotBlank() &&
            manajerinsertUiEvent.kontak_manajer.isNotBlank()
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        OutlinedTextField(
            value = manajerinsertUiEvent.id_manajer,
            onValueChange = {
                if (it.length <= 15){
                onValueChange(manajerinsertUiEvent.copy(id_manajer = it))}},
            label = { Text("Id manajer") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = manajerinsertUiEvent.nama_manajer,
            onValueChange = {
                if (it.length <= 30){
                onValueChange(manajerinsertUiEvent.copy(nama_manajer = it))}},
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = manajerinsertUiEvent.kontak_manajer,
            onValueChange = {
                if (it.length <= 15){
                onValueChange(manajerinsertUiEvent.copy(kontak_manajer = it))}},
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