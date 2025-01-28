package com.example.finalproject_pam.ui.view.properti

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.finalproject_pam.ui.PenyediaViewModel
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import com.example.finalproject_pam.model.Properti
import com.example.finalproject_pam.ui.costumwidget.CostumeTopAppBar
import com.example.finalproject_pam.ui.navigation.DestinasiNavigasi
import com.example.finalproject_pam.ui.viewmodel.properti.PropertiDetailUiState
import com.example.finalproject_pam.ui.viewmodel.properti.PropertiDetailVM


object DestinasiDetailProperti: DestinasiNavigasi {
    override val route = "detail_properti"
    override val titleRes = "Detail properti"
    const val ID_PROPERTI = "id_properti"
    val routesWithArg = "$route/{$ID_PROPERTI}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertiDetailView(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    navigateToJenisHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PropertiDetailVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        modifier = modifier.background(Color(0xFFF5F5F5)), // Warna latar belakang abu-abu muda
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailProperti.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getPropertiById()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemUpdate,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp),
                containerColor = Color(0xFF6200EE) // Warna aksen ungu
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Kontak",
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->
        PropertiDetailStatus(
            modifier = Modifier.padding(innerPadding),
            propertidetailUiState = viewModel.propertiDetailState,
            retryAction = { viewModel.getPropertiById() },
            onDeleteClick = {
                viewModel.deleteProperti(viewModel.propertiDetailState.let { state ->
                    if (state is PropertiDetailUiState.Success) state.properti.id_properti else ""
                })
                navigateBack()
            },
            onJenisClick = navigateToJenisHome
        )
    }
}

@Composable
fun PropertiDetailStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    propertidetailUiState: PropertiDetailUiState,
    onDeleteClick: () -> Unit,
    onJenisClick: () -> Unit
) {
    when (propertidetailUiState) {
        is PropertiDetailUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is PropertiDetailUiState.Success -> {
            if (propertidetailUiState.properti.id_properti.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan", style = MaterialTheme.typography.titleMedium)
                }
            } else {
                ItemDetailPrpt(
                    properti = propertidetailUiState.properti,
                    modifier = modifier.fillMaxWidth(),
                    onDeleteClick = onDeleteClick,
                    onJenisClick = onJenisClick
                )
            }
        }
        is PropertiDetailUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailPrpt(
    modifier: Modifier = Modifier,
    properti: Properti,
    onDeleteClick: () -> Unit,
    onJenisClick: () -> Unit
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    Card(
        modifier = modifier
            .padding(16.dp)
            .shadow(8.dp, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailPrpt(judul = "ID Properti", isinya = properti.id_properti)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailPrpt(judul = "Nama Properti", isinya = properti.nama_properti)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailPrpt(judul = "Deskripsi", isinya = properti.deskripsi_properti)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailPrpt(judul = "Lokasi", isinya = properti.lokasi)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailPrpt(judul = "Harga", isinya = properti.harga)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailPrpt(judul = "Status", isinya = properti.status_properti)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailPrpt(judul = "ID Jenis", isinya = properti.id_jenis)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailPrpt(judul = "ID Manajer", isinya = properti.id_manajer)
            Divider(modifier = Modifier.padding(vertical = 8.dp))

            Spacer(modifier = Modifier.padding(8.dp))

            // Tombol Delete
            Button(
                onClick = { deleteConfirmationRequired = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)) // Warna merah
            ) {
                Text(text = "Delete", color = Color.White)
            }

            Spacer(modifier = Modifier.padding(8.dp))

            // Tombol Lihat Jenis
            Button(
                onClick = onJenisClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03DAC6)) // Warna aksen cyan
            ) {
                Text(text = "Lihat Jenis", color = Color.White)
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
fun ComponentDetailPrpt(
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
    AlertDialog(
        onDismissRequest = {},
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel", color = Color(0xFF6200EE)) // Warna aksen ungu
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes", color = Color(0xFFD32F2F)) // Warna merah
            }
        }
    )
}