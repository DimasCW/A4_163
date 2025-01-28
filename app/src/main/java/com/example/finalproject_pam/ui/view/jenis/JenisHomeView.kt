package com.example.finalproject_pam.ui.view.jenis

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject_pam.R
import com.example.finalproject_pam.model.Jenis
import com.example.finalproject_pam.ui.PenyediaViewModel
import com.example.finalproject_pam.ui.costumwidget.CostumeTopAppBar
import com.example.finalproject_pam.ui.navigation.DestinasiNavigasi
import com.example.finalproject_pam.ui.viewmodel.jenis.JenisHomeUiState
import com.example.finalproject_pam.ui.viewmodel.jenis.JenisHomeVM


object DestinasiHomeJenis : DestinasiNavigasi {
    override val route = "home_jenis"
    override val titleRes = "Jenis"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JenisHomeView(
    navigateBack: () -> Unit,
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: JenisHomeVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeJenis.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getJns()
                },
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp),
                containerColor = Color(0xFF6200EE) // Menggunakan containerColor, bukan backgroundColor
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Jenis", tint = Color.White)
            }
        }
    ) { innerPadding ->
        JenisHomeStatus(
            jenishomeUiState = viewModel.jnsUIState,
            retryAction = { viewModel.getJns() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = { jenis ->
                viewModel.deleteJenis(jenis.id_jenis)
                viewModel.getJns()
            }
        )
    }
}

@Composable
fun JenisHomeStatus(
    jenishomeUiState: JenisHomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Jenis) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (jenishomeUiState) {
        is JenisHomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is JenisHomeUiState.Success -> {
            if (jenishomeUiState.jenis.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Tidak ada data jenis",
                        style = MaterialTheme.typography.titleMedium, // Menggunakan titleMedium
                        color = Color.Gray
                    )
                }
            } else {
                JnsLayout(
                    jenis = jenishomeUiState.jenis,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_jenis) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is JenisHomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = Color(0xFF6200EE)) // Warna ungu modern
    }
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = "Error",
            tint = Color(0xFFD32F2F), // Warna merah modern
            modifier = Modifier.size(64.dp)
        )
        Text(
            text = stringResource(R.string.loading_failed),
            style = MaterialTheme.typography.titleMedium, // Menggunakan titleMedium
            color = Color.Gray,
            modifier = Modifier.padding(16.dp)
        )
        Button(
            onClick = retryAction,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)) // Menggunakan containerColor
        ) {
            Text(stringResource(R.string.retry), color = Color.White)
        }
    }
}

@Composable
fun JnsLayout(
    jenis: List<Jenis>,
    modifier: Modifier = Modifier,
    onDetailClick: (Jenis) -> Unit,
    onDeleteClick: (Jenis) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(jenis) { jenis ->
            JnsCard(
                jenis = jenis,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(jenis) },
                onDeleteClick = { onDeleteClick(jenis) }
            )
        }
    }
}

@Composable
fun JnsCard(
    jenis: Jenis,
    modifier: Modifier = Modifier,
    onDeleteClick: (Jenis) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(8.dp, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5)) // Warna latar belakang abu-abu muda
                .padding(16.dp)
        ) {
            // Bagian kiri: Logo atau gambar
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color(0xFF6200EE), shape = RoundedCornerShape(8.dp)), // Warna ungu modern
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Logo",
                    modifier = Modifier.size(40.dp),
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Bagian kanan: Informasi jenis
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = jenis.id_jenis,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color(0xFF6200EE) // Warna ungu modern
                )
                Text(
                    text = jenis.nama_jenis,
                    style = MaterialTheme.typography.bodyMedium, // Menggunakan bodyMedium
                    color = Color(0xFF424242) // Warna teks abu-abu gelap
                )
                Text(
                    text = "Deskripsi: ${jenis.deskripsi_jenis}",
                    style = MaterialTheme.typography.bodySmall, // Menggunakan bodySmall
                    color = Color(0xFF757575) // Warna teks abu-abu
                )
            }

            // Tombol delete
            IconButton(onClick = { onDeleteClick(jenis) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color(0xFFD32F2F) // Warna merah modern
                )
            }
        }
    }
}