package com.dag.nexq_app.presentation.main_activity.components.bottom_navbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dag.nexq_app.base.navigation.Destination

@Composable
fun BottomNavbar(
    items: List<NavItem>,
    currentDestination: String,
    onClick: (destination: Destination) -> Unit
    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .shadow(elevation = 8.dp, spotColor = Color.LightGray.copy(alpha = 0.3f)),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items.forEach { item ->
            BottomNavbarItem(
                isActive = item.destination.toString() == currentDestination,
                navItem = item,
                onClick = onClick
            )
        }
    }

}


@Composable
fun BottomNavbarItem(
    modifier: Modifier = Modifier,
    isActive: Boolean = false,
    navItem: NavItem,
    onClick: (destination: Destination) -> Unit
) {
    Box(modifier = modifier.padding(8.dp)) {
        IconButton(
            modifier = Modifier
                .padding(16.dp)
                .clip(CircleShape)
                .background(
                    if (isActive) Color.Red else Color.Transparent
                ),
            onClick = { onClick(navItem.destination) },
        ) {
            Icon(
                navItem.icon,
                tint = if (isActive) Color.White else Color.Black,
                contentDescription = "Add"
            )
        }
    }
}


@Composable
@Preview
fun BottomNavbarItemPassivePreview() {
    BottomNavbarItem(navItem = NavItem.HOME) {}
}

@Composable
@Preview
fun BottomNavbarItemActivePreview() {
    BottomNavbarItem(isActive = true, navItem = NavItem.ADD) {}
}


@Composable
@Preview
fun BottomNavbarPreview() {
    BottomNavbar(
        items = NavItem.values().asList(),
        currentDestination = Destination.Add.toString()
    ) {}
}