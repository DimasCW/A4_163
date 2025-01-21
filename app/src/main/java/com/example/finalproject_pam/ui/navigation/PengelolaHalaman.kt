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
import com.example.finalproject_pam.ui.view.pemilik.PemilikHomeView

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
//        composable(DestinasiEntry.route){
//            EntryMhsScreen(navigateBack = {
//                navController.navigate(DestinasiHomePemilik.route){
//                    popUpTo(DestinasiHomePemilik.route){
//                        inclusive = true
//                    }
//                }
//            })
//        }
//        composable(DestinasiDetail.routesWithArg, arguments = listOf(navArgument(DestinasiDetail.NIM) {
//            type = NavType.StringType })
//        ){
//            val nim = it.arguments?.getString(DestinasiDetail.NIM)
//            nim?.let { nim ->
//                DetailView(
//                    navigateToItemUpdate = { navController.navigate("${DestinasiUpdate.route}/$nim") },
//                    navigateBack = { navController.navigate(DestinasiHomePemilik.route) {
//                        popUpTo(DestinasiHomePemilik.route) { inclusive = true }
//                    }
//                    }
//                )
//            }
//        }
//        composable(DestinasiUpdate.routesWithArg, arguments = listOf(navArgument(DestinasiDetail.NIM){
//            type = NavType.StringType })
//        ){
//            val nim = it.arguments?.getString(DestinasiUpdate.NIM)
//            nim?.let { nim ->
//                UpdateView(
//                    onBack = { navController.popBackStack() },
//                    onNavigate = { navController.popBackStack() }
//                )
//            }
//        }
    }
}