package com.example.finalproject_pam.ui.view.pemilik

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
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject_pam.model.Pemilik
import com.example.finalproject_pam.ui.PenyediaViewModel
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import com.example.finalproject_pam.ui.costumwidget.CostumeTopAppBar
import com.example.finalproject_pam.ui.navigation.DestinasiNavigasi
import com.example.finalproject_pam.ui.viewmodel.pemilik.PemilikDetailUiState
import com.example.finalproject_pam.ui.viewmodel.pemilik.PemilikDetailVM


object DestinasiDetailPemilik: DestinasiNavigasi {
    override val route = "detail_pemilik"
    override val titleRes = "Detail Pemilik"
    const val ID_PEMILIK = "id_pemilik"
    val routesWithArg = "$route/{$ID_PEMILIK}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PemilikDetailView(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PemilikDetailVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailPemilik.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getPemilikById()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemUpdate,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp),
                containerColor = MaterialTheme.colorScheme.primary // Warna aksen
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Kontak",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { innerPadding ->
        PemilikDetailStatus(
            modifier = Modifier.padding(innerPadding),
            pemilikdetailUiState = viewModel.pemilikDetailState,
            retryAction = { viewModel.getPemilikById() },
            onDeleteClick = {
                viewModel.deletePemilik(viewModel.pemilikDetailState.let { state ->
                    if (state is PemilikDetailUiState.Success) state.pemilik.id_pemilik else ""
                })
                navigateBack()
            }
        )
    }
}

@Composable
fun PemilikDetailStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    pemilikdetailUiState: PemilikDetailUiState,
    onDeleteClick: () -> Unit
) {
    when (pemilikdetailUiState) {
        is PemilikDetailUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is PemilikDetailUiState.Success -> {
            if (pemilikdetailUiState.pemilik.id_pemilik.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Data tidak ditemukan",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            } else {
                ItemDetailPmlk(
                    pemilik = pemilikdetailUiState.pemilik,
                    modifier = modifier.fillMaxWidth(),
                    onDeleteClick = onDeleteClick
                )
            }
        }
        is PemilikDetailUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailPmlk(
    modifier: Modifier = Modifier,
    pemilik: Pemilik,
    onDeleteClick: () -> Unit
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailPmlk(judul = "ID PEMILIK", isinya = pemilik.id_pemilik)
            Divider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.outline)
            ComponentDetailPmlk(judul = "Nama Pemilik", isinya = pemilik.nama_pemilik)
            Divider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.outline)
            ComponentDetailPmlk(judul = "Kontak Pemilik", isinya = pemilik.kontak_pemilik)
            Divider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.outline)

            Spacer(modifier = Modifier.padding(8.dp))

            Button(
                onClick = {
                    deleteConfirmationRequired = true
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text(text = "Delete", color = MaterialTheme.colorScheme.onError)
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
fun ComponentDetailPmlk(
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
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = isinya,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { onDeleteCancel() },
        title = {
            Text(
                text = "Delete Data",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Text(
                text = "Apakah anda yakin ingin menghapus data?",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel", color = MaterialTheme.colorScheme.onSurface)
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes", color = MaterialTheme.colorScheme.error)
            }
        }
    )
}
