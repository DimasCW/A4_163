package com.example.finalproject_pam.ui.view.properti

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
import androidx.compose.material.icons.filled.Home
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
import com.example.finalproject_pam.model.Properti
import com.example.finalproject_pam.ui.PenyediaViewModel
import com.example.finalproject_pam.ui.costumwidget.CostumeTopAppBar
import com.example.finalproject_pam.ui.navigation.DestinasiNavigasi
import com.example.finalproject_pam.ui.viewmodel.properti.PropertiHomeUiState
import com.example.finalproject_pam.ui.viewmodel.properti.PropertiHomeVM


object DestinasiHomeProperti : DestinasiNavigasi {
    override val route = "home_properti"
    override val titleRes = "Properti"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertiHomeView(
    navigateBack: () -> Unit,
    navigateToItemEntry: () -> Unit,
    navigateToPemilikHome: () -> Unit,
    navigateToManajerHome: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: PropertiHomeVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeProperti.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getPrpt()
                },
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp),
                containerColor = Color(0xFF6200EE) // Warna aksen ungu
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Properti", tint = Color.White)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFFF5F5F5)) // Warna latar belakang abu-abu muda
        ) {
            // Tombol untuk navigasi ke PemilikHomeView dan ManajerHomeView
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = navigateToPemilikHome,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03DAC6)) // Warna aksen cyan
                ) {
                    Text(text = "Lihat Pemilik", color = Color.White)
                }

                Button(
                    onClick = navigateToManajerHome,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF018786)) // Warna aksen teal
                ) {
                    Text(text = "Lihat Manajer", color = Color.White)
                }
            }

            // Tampilan daftar properti
            PropertiHomeStatus(
                propertihomeUiState = viewModel.prptUIState,
                retryAction = { viewModel.getPrpt() },
                modifier = Modifier.fillMaxWidth(),
                onDetailClick = onDetailClick,
                onDeleteClick = { properti ->
                    viewModel.deleteProperti(properti.id_properti)
                    viewModel.getPrpt()
                }
            )
        }
    }
}

@Composable
fun PropertiHomeStatus(
    propertihomeUiState: PropertiHomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Properti) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (propertihomeUiState) {
        is PropertiHomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is PropertiHomeUiState.Success -> {
            if (propertihomeUiState.properti.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Properti", style = MaterialTheme.typography.titleMedium)
                }
            } else {
                PrptLayout(
                    properti = propertihomeUiState.properti,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_properti) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is PropertiHomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color(0xFF6200EE)) // Warna aksen ungu
    }
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.loading_failed), style = MaterialTheme.typography.titleMedium)
        Button(
            onClick = retryAction,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)) // Warna aksen ungu
        ) {
            Text(stringResource(R.string.retry), color = Color.White)
        }
    }
}

@Composable
fun PrptLayout(
    properti: List<Properti>,
    modifier: Modifier = Modifier,
    onDetailClick: (Properti) -> Unit,
    onDeleteClick: (Properti) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(properti) { properti ->
            PrptCard(
                properti = properti,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(properti) },
                onDeleteClick = { onDeleteClick(properti) }
            )
        }
    }
}

@Composable
fun PrptCard(
    properti: Properti,
    modifier: Modifier = Modifier,
    onDeleteClick: (Properti) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(8.dp, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Bagian kiri: Logo atau gambar
            val backgroundColor = when (properti.status_properti) {
                "Tersedia" -> Color(0xFF4CAF50) // Hijau
                "Dijual" -> Color(0xFFF44336) // Merah
                "Tersewa" -> Color(0xFFFFEB3B) // Kuning
                else -> Color(0xFF6200EE) // Default ungu
            }
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(backgroundColor, shape = RoundedCornerShape(8.dp)), // Warna sesuai status
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Properti",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Bagian kanan: Informasi properti
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = properti.nama_properti,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color(0xFF000000) // Warna teks hitam
                )
                Text(
                    text = "Status : ${properti.status_properti}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF757575) // Warna teks abu-abu
                )
                Text(
                    text = "Rp ${properti.harga}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF757575) // Warna teks abu-abu
                )
            }

            // Tombol delete
            IconButton(onClick = { onDeleteClick(properti) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color(0xFFD32F2F) // Warna merah
                )
            }
        }
    }
}