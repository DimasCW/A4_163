package com.example.finalproject_pam.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.finalproject_pam.ui.view.jenis.DestinasiDetailJenis
import com.example.finalproject_pam.ui.view.jenis.DestinasiEntryJenis
import com.example.finalproject_pam.ui.view.jenis.EntryJnsScreen
import com.example.finalproject_pam.ui.view.jenis.JenisDetailView
import com.example.finalproject_pam.ui.view.jenis.JenisHomeView
import com.example.finalproject_pam.ui.view.pemilik.DestinasiDetailPemilik
import com.example.finalproject_pam.ui.view.pemilik.DestinasiEntryPemilik
import com.example.finalproject_pam.ui.view.jenis.DestinasiUpdateJenis
import com.example.finalproject_pam.ui.view.pemilik.DestinasiUpdatePemilik
import com.example.finalproject_pam.ui.view.pemilik.EntryPmlkScreen
import com.example.finalproject_pam.ui.view.jenis.JenisUpdateView
import com.example.finalproject_pam.ui.view.manajer.DestinasiDetailManajer
import com.example.finalproject_pam.ui.view.manajer.DestinasiEntryManajer
import com.example.finalproject_pam.ui.view.manajer.DestinasiUpdateManajer
import com.example.finalproject_pam.ui.view.manajer.EntryMnjrScreen
import com.example.finalproject_pam.ui.view.manajer.ManajerDetailView
import com.example.finalproject_pam.ui.view.manajer.ManajerHomeView
import com.example.finalproject_pam.ui.view.manajer.ManajerUpdateView
import com.example.finalproject_pam.ui.view.pemilik.PemilikDetailView
import com.example.finalproject_pam.ui.view.pemilik.PemilikHomeView
import com.example.finalproject_pam.ui.view.pemilik.PemilikUpdateView


object DestinasiHomePemilik : DestinasiNavigasi{
    override val route = "home_pemilik"
    override val titleRes = "Pemilik"
}
object DestinasiHomeJenis : DestinasiNavigasi{
    override val route = "home_jenis"
    override val titleRes = "Jenis"
}

object DestinasiHomeManajer : DestinasiNavigasi{
    override val route = "home_manajer"
    override val titleRes = "Manajer"
}


@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()){
    NavHost(
        navController=navController,
        startDestination = DestinasiBeranda .route,
        modifier = Modifier,
    ){
        composable(DestinasiBeranda.route) {
            BerandaView(
                onPemilik = {
                    navController.navigate(DestinasiHomePemilik.route)
                            },
                onJenis = {
                    navController.navigate(DestinasiHomeJenis.route)
                },
                onManajer = {
                    navController.navigate(DestinasiHomeManajer.route)
                }
            )
        }

        //        Manajer Navigasi
//
        composable(DestinasiHomeManajer.route){
            ManajerHomeView(
                navigateToItemEntry = {navController.navigate(DestinasiEntryManajer.route)},
                onDetailClick = { id_manajer ->
                    navController.navigate("${DestinasiDetailManajer.route}/$id_manajer")
                },
                navigateBack = { navController.popBackStack() },
            )
        }
        composable(DestinasiEntryManajer.route){
            EntryMnjrScreen(navigateBack = {
                navController.navigate(DestinasiHomeManajer.route){
                    popUpTo(DestinasiHomeManajer.route){
                        inclusive = true
                    }
                }
            })
        }
        composable(
            DestinasiDetailManajer.routesWithArg, arguments = listOf(navArgument(DestinasiDetailManajer.ID_MANAJER) {
            type = NavType.StringType })
        ){
            val id_manajer = it.arguments?.getString(DestinasiDetailManajer.ID_MANAJER)
            id_manajer?.let { id_manajer ->
                ManajerDetailView(
                    navigateToItemUpdate = { navController.navigate("${DestinasiUpdateManajer.route}/$id_manajer") },
                    navigateBack = { navController.navigate(DestinasiHomeManajer.route) {
                        popUpTo(DestinasiHomeManajer.route) { inclusive = true }
                    }
                    }
                )
            }
        }
        composable(
            DestinasiUpdateManajer.routesWithArg, arguments = listOf(navArgument(DestinasiDetailManajer.ID_MANAJER){
                type = NavType.StringType })
        ){
            val id_manajer = it.arguments?.getString(DestinasiUpdateManajer.ID_MANAJER)
            id_manajer?.let { id_manajer ->
                ManajerUpdateView(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }


//        Jenis Navigasi
//
        composable(DestinasiHomeJenis.route){
            JenisHomeView(
                navigateToItemEntry = {navController.navigate(DestinasiEntryJenis.route)},
                onDetailClick = { id_jenis ->
                    navController.navigate("${DestinasiDetailJenis.route}/$id_jenis")
                },
                navigateBack = { navController.popBackStack() },
            )
        }
        composable(DestinasiEntryJenis.route){
            EntryJnsScreen(navigateBack = {
                navController.navigate(DestinasiHomeJenis.route){
                    popUpTo(DestinasiHomeJenis.route){
                        inclusive = true
                    }
                }
            })
        }
        composable(DestinasiDetailJenis.routesWithArg, arguments = listOf(navArgument(DestinasiDetailJenis.ID_JENIS) {
            type = NavType.StringType })
        ){
            val id_jenis = it.arguments?.getString(DestinasiDetailJenis.ID_JENIS)
            id_jenis?.let { id_jenis ->
                JenisDetailView(
                    navigateToItemUpdate = { navController.navigate("${DestinasiUpdateJenis.route}/$id_jenis") },
                    navigateBack = { navController.navigate(DestinasiHomeJenis.route) {
                        popUpTo(DestinasiHomeJenis.route) { inclusive = true }
                    }
                    }
                )
            }
        }
        composable(
            DestinasiUpdateJenis.routesWithArg, arguments = listOf(navArgument(DestinasiUpdateJenis.ID_Jenis){
            type = NavType.StringType })
        ){
            val id_jenis = it.arguments?.getString(DestinasiUpdateJenis.ID_Jenis)
            id_jenis?.let { id_jenis ->
                JenisUpdateView(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }


//        Pemilik Navigasi
        composable(DestinasiHomePemilik.route){
            PemilikHomeView(
                navigateToItemEntry = {navController.navigate(DestinasiEntryPemilik.route)},
                onDetailClick = { id_pemilik ->
                    navController.navigate("${DestinasiDetailPemilik.route}/$id_pemilik")
                },
                navigateBack = { navController.popBackStack() },
            )
        }
        composable(DestinasiEntryPemilik.route){
            EntryPmlkScreen(navigateBack = {
                navController.navigate(DestinasiHomePemilik.route){
                    popUpTo(DestinasiHomePemilik.route){
                        inclusive = true
                    }
                }
            })
        }
        composable(DestinasiDetailPemilik.routesWithArg, arguments = listOf(navArgument(DestinasiDetailPemilik.ID_PEMILIK) {
            type = NavType.StringType })
        ){
            val id_pemilik = it.arguments?.getString(DestinasiDetailPemilik.ID_PEMILIK)
            id_pemilik?.let { id_pemilik ->
                PemilikDetailView(
                    navigateToItemUpdate = { navController.navigate("${DestinasiUpdatePemilik.route}/$id_pemilik") },
                    navigateBack = { navController.navigate(DestinasiHomePemilik.route) {
                        popUpTo(DestinasiHomePemilik.route) { inclusive = true }
                    }
                    }
                )
            }
        }
        composable(DestinasiUpdatePemilik.routesWithArg, arguments = listOf(navArgument(DestinasiDetailPemilik.ID_PEMILIK){
            type = NavType.StringType })
        ){
            val id_pemilik = it.arguments?.getString(DestinasiUpdatePemilik.ID_PEMILIK)
            id_pemilik?.let { id_pemilik ->
                PemilikUpdateView(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }
    }
}