package com.example.pocketledger.navigations

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.example.pocketledger.ui.screens.setting.SettingsScreen
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pocketledger.ui.components.PocketLedgerBottomBar
import com.example.pocketledger.ui.screens.home.HomeRoute
import com.example.pocketledger.ui.screens.transaction.TransactionRoute


@Composable
fun AppNavigation(){

    val navController = rememberNavController()

    val navBackStackEntry by
    navController.currentBackStackEntryAsState()

    val currentRoute =
        navBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            PocketLedgerBottomBar(
                currentRoute=currentRoute,
                onNavigate = {
                    route ->
                    navController.navigate(route){
                        popUpTo(
                            navController.graph.findStartDestination().id
                        ){
                            saveState =true
                        }
                        launchSingleTop =true
                        restoreState = true
                    }
                }
            )
        }
    ) {
        innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppDestination.Home.route,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(AppDestination.Home.route){
                HomeRoute()
            }
            composable(AppDestination.Transactions.route){
                TransactionRoute()
            }
            composable(AppDestination.Settings.route){
                SettingsScreen()
            }
        }

    }

}


