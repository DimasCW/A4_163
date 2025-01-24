package com.example.finalproject_pam.ui.view.jenis

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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject_pam.model.Jenis
import com.example.finalproject_pam.ui.PenyediaViewModel
import com.example.finalproject_pam.ui.costumwidget.CostumeTopAppBar
import com.example.finalproject_pam.ui.navigation.DestinasiNavigasi
import com.example.finalproject_pam.ui.viewmodel.jenis.JenisDetailUiState
import com.example.finalproject_pam.ui.viewmodel.jenis.JenisDetailVM

object DestinasiDetailJenis: DestinasiNavigasi {
    override val route = "detail_jenis"
    override val titleRes = "Detail Jenis"
    const val ID_Jenis = "id_Jenis"
    val routesWithArg = "$route/{$ID_Jenis}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JenisDetailView(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: JenisDetailVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailJenis.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getJenisById()
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
        JenisDetailStatus(
            modifier = Modifier.padding(innerPadding),
            jenisdetailUiState = viewModel.jenisDetailState,
            retryAction = { viewModel.getJenisById() },
            onDeleteClick = {
                viewModel.deleteJenis(viewModel.jenisDetailState.let { state ->
                    if (state is JenisDetailUiState.Success) state.jenis.id_jenis else ""
                })
                navigateBack()
            }
        )
    }
}


@Composable
fun JenisDetailStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    jenisdetailUiState: JenisDetailUiState,
    onDeleteClick: () -> Unit
) {
    when (jenisdetailUiState) {
        is JenisDetailUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is JenisDetailUiState.Success -> {
            if (jenisdetailUiState.jenis.id_jenis.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { Text("Data tidak ditemukan") }
            } else {
                ItemDetailJns(
                    jenis = jenisdetailUiState.jenis,
                    modifier = modifier.fillMaxWidth(),
                    onDeleteClick = onDeleteClick
                )
            }
        }
        is JenisDetailUiState.Error -> OnError(retryAction,modifier = modifier.fillMaxSize()
        )
    }
}


@Composable
fun ItemDetailJns(
    modifier: Modifier = Modifier,
    jenis: Jenis,
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
            ComponentDetailJns(judul = "ID Jenis", isinya = jenis.id_jenis)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailJns(judul = "Nama Jenis", isinya = jenis.nama_jenis)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailJns(judul = "Kontak Jenis", isinya = jenis.deskripsi_jenis)
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
fun ComponentDetailJns(
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