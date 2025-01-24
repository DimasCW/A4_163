package com.example.finalproject_pam.ui.view.jenis
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject_pam.ui.PenyediaViewModel
import com.example.finalproject_pam.ui.costumwidget.CostumeTopAppBar
import com.example.finalproject_pam.ui.navigation.DestinasiNavigasi
import com.example.finalproject_pam.ui.viewmodel.jenis.JenisUpdateVM
import com.example.finalproject_pam.ui.view.jenis.EntryBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdateJenis: DestinasiNavigasi {
    override val route = "update_jenis"
    override val titleRes = "Update Jenis"
    const val ID_Jenis = "id_jenis"
    val routesWithArg = "$route/{$ID_Jenis}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JenisUpdateView(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate:()-> Unit,
    viewModel: JenisUpdateVM = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateJenis.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ){ padding ->
        EntryBody(
            modifier = Modifier.padding(padding),
            jenisinsertUiState = viewModel.UpdateUiState,
            onJenisValueChange = viewModel::updateInsertJnsState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateJns()
                    delay(600)
                    withContext(Dispatchers.Main){
                        onNavigate()
                    }
                }
            }
        )
    }
}

