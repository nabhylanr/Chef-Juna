package com.example.chefjuna.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigationBar(selectedIndex: Int, onItemSelected: (Int) -> Unit) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 4.dp
    ) {
        val items = listOf("Home", "Explore", "Favorite", "Profile")
        val icons = listOf(
            Icons.Default.Home,
            Icons.Default.Search,
            Icons.Default.Favorite,
            Icons.Default.Person
        )

        items.forEachIndexed { index, label ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = { onItemSelected(index) },
                icon = {
                    Icon(
                        imageVector = icons[index],
                        contentDescription = label,
                        tint = if (selectedIndex == index) Color.Gray else Color(0xFF508130)
                    )
                },
                label = {
                    Text(
                        label,
                        color = if (selectedIndex == index) Color.Gray else Color(0xFF508130)
                    )
                }
            )
        }
    }
}
