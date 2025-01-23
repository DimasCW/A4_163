package com.example.finalproject_pam.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.finalproject_pam.ui.view.pemilik.DestinasiDetail
import com.example.finalproject_pam.ui.view.pemilik.DestinasiEntry
import com.example.finalproject_pam.ui.view.pemilik.DestinasiUpdate
import com.example.finalproject_pam.ui.view.pemilik.EntryPmlkScreen
import com.example.finalproject_pam.ui.view.pemilik.PemilikDetailView
import com.example.finalproject_pam.ui.view.pemilik.PemilikHomeView
import com.example.finalproject_pam.ui.view.pemilik.PemilikUpdateView

object DestinasiHomePemilik : DestinasiNavigasi{
    override val route = "home"
    override val titleRes = "Pemilik"
}

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()){
    NavHost(
        navController=navController,
        startDestination = DestinasiHomePemilik.route,
        modifier = Modifier,
    ){
        composable(DestinasiHomePemilik.route){
            PemilikHomeView(
                navigateToItemEntry = {navController.navigate(DestinasiEntry.route)},
                onDetailClick = { id_pemilik ->
                    navController.navigate("${DestinasiDetail.route}/$id_pemilik")
                }
            )
        }
        composable(DestinasiEntry.route){
            EntryPmlkScreen(navigateBack = {
                navController.navigate(DestinasiHomePemilik.route){
                    popUpTo(DestinasiHomePemilik.route){
                        inclusive = true
                    }
                }
            })
        }
        composable(DestinasiDetail.routesWithArg, arguments = listOf(navArgument(DestinasiDetail.ID_PEMILIK) {
            type = NavType.StringType })
        ){
            val id_pemilik = it.arguments?.getString(DestinasiDetail.ID_PEMILIK)
            id_pemilik?.let { id_pemilik ->
                PemilikDetailView(
                    navigateToItemUpdate = { navController.navigate("${DestinasiUpdate.route}/$id_pemilik") },
                    navigateBack = { navController.navigate(DestinasiHomePemilik.route) {
                        popUpTo(DestinasiHomePemilik.route) { inclusive = true }
                    }
                    }
                )
            }
        }
        composable(DestinasiUpdate.routesWithArg, arguments = listOf(navArgument(DestinasiDetail.ID_PEMILIK){
            type = NavType.StringType })
        ){
            val id_pemilik = it.arguments?.getString(DestinasiUpdate.ID_PEMILIK)
            id_pemilik?.let { id_pemilik ->
                PemilikUpdateView(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }
    }
}