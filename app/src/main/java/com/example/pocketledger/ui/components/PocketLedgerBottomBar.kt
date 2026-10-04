package com.example.pocketledger.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import com.example.pocketledger.navigations.AppDestination
import androidx.compose.material3.Text

import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SwapHoriz

@Composable
fun PocketLedgerBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
){

    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == AppDestination.Home.route,
            onClick = { onNavigate(AppDestination.Home.route) },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Home"
                )
            },
               label = {
                   Text(text = "Home")

            }
        )
        NavigationBarItem(
            selected = currentRoute == AppDestination.Transactions.route,
            onClick = { onNavigate(AppDestination.Transactions.route) },
            icon = {
                Icon(
                    imageVector = Icons.Filled.SwapHoriz,
                    contentDescription = "Transactions"
                )
            },
            label = {
                Text(text = "Transactions")

            }
        )
        NavigationBarItem(
            selected = currentRoute == AppDestination.Settings.route,
            onClick = { onNavigate(AppDestination.Settings.route) },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Settings"
                )
            },
            label = {
                Text(text = "Settings")

            }
        )
    }

}