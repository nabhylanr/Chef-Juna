package com.example.chefjuna.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.chefjuna.ui.model.Dish
import com.example.chefjuna.R
import com.example.chefjuna.ui.components.SectionTitle

@Composable
fun HomeScreen(
    onDishClick: (Dish) -> Unit,
    searchQuery: String,
    onSearchChanged: (String) -> Unit,
    isSearching: Boolean
) {
    val allDishes = listOf(
        Dish("Avocado Toast", "245 kcal", "$9.99", R.drawable.avocado_toast),
        Dish("Italian Salad", "380 kcal", "$12.50", R.drawable.italian_salad),
        Dish("Poached Egg", "180 kcal", "$8.75", R.drawable.poached_egg),
        Dish("Cooked Noodles", "420 kcal", "$11.25", R.drawable.cooked_noodles)
    )

    val filteredDishes = if (searchQuery.isEmpty()) {
        allDishes
    } else {
        allDishes.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "Good morning", style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = "Arnold Poernomo",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF508130)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.selfie),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChanged,
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            placeholder = { Text("Search dishes") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (isSearching) {
            if (filteredDishes.isNotEmpty()) {
                SectionTitle(title = "Search Results")
                DishRow(
                    dishes = filteredDishes,
                    onDishClick = onDishClick,
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                )
            } else {
                Text("No dishes found matching \"$searchQuery\"",
                    modifier = Modifier.padding(vertical = 16.dp))
            }
        } else {
            SectionTitle(title = "Popular dishes")
            DishRow(
                dishes = listOf(
                    Dish("Avocado Toast", "245 kcal", "$9.99", R.drawable.avocado_toast),
                    Dish("Italian Salad", "380 kcal", "$12.50", R.drawable.italian_salad)
                ),
                onDishClick = onDishClick,
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            SectionTitle(title = "Recipe of the week")
            DishRow(
                dishes = listOf(
                    Dish("Poached Egg", "180 kcal", "$8.75", R.drawable.poached_egg),
                    Dish("Cooked Noodles", "420 kcal", "$11.25", R.drawable.cooked_noodles)
                ),
                onDishClick = onDishClick,
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            onClick = { /* Handle click */ },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Cart",
                tint = Color.White,
                modifier = Modifier
                    .background(Color(0xFF52C17D), shape = CircleShape)
                    .padding(8.dp)
            )
        }
    }
}
