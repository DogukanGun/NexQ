package com.dag.nexq_app.presentation.mainActivity.components.bottom_navbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.dag.nexq_app.base.navigation.Destination

enum class NavItem(val icon: ImageVector, val destination:Destination) {
    HOME(Icons.Filled.Home,Destination.HomeScreen),
    ADD(Icons.Filled.Add,Destination.Add),
    Profile(Icons.Filled.Person,Destination.Profile)
}