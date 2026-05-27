package com.example.gs.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.gs.ui.screens.HomeScreen
import com.example.gs.ui.screens.ShipmentDetailScreen
import com.example.gs.ui.screens.ShipmentsScreen
import com.example.gs.ui.screens.TrackingScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Home.route,
        modifier = modifier
    ) {
        composable(Routes.Home.route) {
            HomeScreen(
                onOpenShipments = { navController.navigate(Routes.Shipments.route) },
                onOpenTracking = { navController.navigate(Routes.Tracking.route) }
            )
        }
        composable(Routes.Shipments.route) {
            ShipmentsScreen(
                onBack = { navController.popBackStack() },
                onShipmentSelected = { shipmentId ->
                    navController.navigate(Routes.ShipmentDetail.createRoute(shipmentId))
                },
                onOpenTracking = { navController.navigate(Routes.Tracking.route) }
            )
        }
        composable(
            route = Routes.ShipmentDetail.route,
            arguments = listOf(navArgument("shipmentId") { defaultValue = "" })
        ) { backStackEntry ->
            ShipmentDetailScreen(
                shipmentId = backStackEntry.arguments?.getString("shipmentId").orEmpty(),
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.Tracking.route) {
            TrackingScreen(
                onBack = { navController.popBackStack() },
                onOpenShipments = { navController.navigate(Routes.Shipments.route) }
            )
        }
    }
}

