package com.example.finalproject_pam.ui.view.pemilik

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
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
import com.example.finalproject_pam.ui.PenyediaViewModel
import com.example.finalproject_pam.ui.costumwidget.CostumeTopAppBar
import com.example.finalproject_pam.ui.navigation.DestinasiNavigasi
import com.example.finalproject_pam.model.Pemilik
import com.example.finalproject_pam.ui.viewmodel.pemilik.PemilikHomeUiState
import com.example.finalproject_pam.ui.viewmodel.pemilik.PemilikHomeVM

object DestinasiHomePemilik : DestinasiNavigasi {
    override val route = "home_pemilik"
    override val titleRes = "Pemilik"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PemilikHomeView(
    navigateBack: () -> Unit,
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: PemilikHomeVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomePemilik.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getPmlk()
                },
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp),
                containerColor = Color(0xFF6200EE) // Warna aksen
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Pemilik", tint = Color.White)
            }
        }
    ) { innerPadding ->
        PemilikHomeStatus(
            pemilikhomeUiState = viewModel.pmlkUIState,
            retryAction = { viewModel.getPmlk() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = { pemilik ->
                viewModel.deletePemilik(pemilik.id_pemilik)
                viewModel.getPmlk()
            }
        )
    }
}

@Composable
fun PemilikHomeStatus(
    pemilikhomeUiState: PemilikHomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pemilik) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (pemilikhomeUiState) {
        is PemilikHomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is PemilikHomeUiState.Success -> {
            if (pemilikhomeUiState.pemilik.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data pemilik", style = MaterialTheme.typography.titleLarge)
                }
            } else {
                PmlkLayout(
                    pemilik = pemilikhomeUiState.pemilik,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_pemilik) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is PemilikHomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = Color(0xFF6200EE))
    }
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.loading_failed), style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))) {
            Text(stringResource(R.string.retry), color = Color.White)
        }
    }
}

@Composable
fun PmlkLayout(
    pemilik: List<Pemilik>,
    modifier: Modifier = Modifier,
    onDetailClick: (Pemilik) -> Unit,
    onDeleteClick: (Pemilik) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pemilik) { pemilik ->
            PmlkCard(
                pemilik = pemilik,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(pemilik) },
                onDeleteClick = { onDeleteClick(pemilik) }
            )
        }
    }
}

@Composable
fun PmlkCard(
    pemilik: Pemilik,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pemilik) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(4.dp, shape = RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Bagian kiri: Logo atau gambar
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color(0xFF6200EE), shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Logo",
                    modifier = Modifier.size(30.dp),
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Bagian kanan: Informasi pemilik
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = pemilik.nama_pemilik,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color(0xFF000000)
                )
                Text(
                    text = "Kontak: ${pemilik.kontak_pemilik}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF666666)
                )
            }

            // Tombol delete
            IconButton(onClick = { onDeleteClick(pemilik) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color(0xFFD32F2F)
                )
            }
        }
    }
}