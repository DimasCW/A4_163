package com.example.finalproject_pam.ui.view.manajer

import com.example.finalproject_pam.model.Manajer
import com.example.finalproject_pam.ui.viewmodel.manajer.ManajerDetailUiState
import com.example.finalproject_pam.ui.viewmodel.manajer.ManajerDetailVM
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject_pam.ui.PenyediaViewModel
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import com.example.finalproject_pam.ui.costumwidget.CostumeTopAppBar
import com.example.finalproject_pam.ui.navigation.DestinasiNavigasi


object DestinasiDetailManajer: DestinasiNavigasi {
    override val route = "detail_manajer"
    override val titleRes = "Detail Manajer"
    const val ID_MANAJER = "id_manajer"
    val routesWithArg = "$route/{$ID_MANAJER}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManajerDetailView(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ManajerDetailVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailManajer.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getManajerById()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemUpdate,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Kontak"
                )
            }
        }
    ) { innerPadding ->
        ManajerDetailStatus(
            modifier = Modifier.padding(innerPadding),
            manajerdetailUiState = viewModel.manajerDetailState,
            retryAction = { viewModel.getManajerById() },
            onDeleteClick = {
                viewModel.deleteManajer(viewModel.manajerDetailState.let { state ->
                    if (state is ManajerDetailUiState.Success) state.manajer.id_manajer else ""
                })
                navigateBack()
            }
        )
    }
}


@Composable
fun ManajerDetailStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    manajerdetailUiState: ManajerDetailUiState,
    onDeleteClick: () -> Unit
) {
    when (manajerdetailUiState) {
        is ManajerDetailUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is ManajerDetailUiState.Success -> {
            if (manajerdetailUiState.manajer.id_manajer.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { Text("Data tidak ditemukan") }
            } else {
                ItemDetailMnjr(
                    manajer = manajerdetailUiState.manajer,
                    modifier = modifier.fillMaxWidth(),
                    onDeleteClick = onDeleteClick
                )
            }
        }
        is ManajerDetailUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}


@Composable
fun ItemDetailMnjr(
    modifier: Modifier = Modifier,
    manajer: Manajer,
    onDeleteClick: () -> Unit
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailMnjr(judul = "ID Manajer", isinya = manajer.id_manajer)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailMnjr(judul = "Nama Manajer", isinya = manajer.nama_manajer)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailMnjr(judul = "Kontak Manajer", isinya = manajer.kontak_manajer)
            Divider(modifier = Modifier.padding(vertical = 8.dp))

            Spacer(modifier = Modifier.padding(8.dp))

            Button(
                onClick = {
                    deleteConfirmationRequired = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Delete")
            }

            if (deleteConfirmationRequired) {
                DeleteConfirmationDialog(
                    onDeleteConfirm = {
                        deleteConfirmationRequired = false
                        onDeleteClick()
                    },
                    onDeleteCancel = { deleteConfirmationRequired = false },
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}


@Composable
fun ComponentDetailMnjr(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = judul,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = isinya,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = {},
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes")
            }
        })
}
