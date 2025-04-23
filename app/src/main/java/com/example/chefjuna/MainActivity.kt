package com.example.chefjuna

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.chefjuna.ui.theme.ChefJunaTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChefJunaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        ChefJunaApp()
                    }
                }
            }
        }
    }
}

@Composable
fun ChefJunaApp() {
    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(selectedIndex = selectedIndex, onItemSelected = {
                selectedIndex = it
            })
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedIndex) {
                0 -> HomeScreen()
                1 -> ExploreScreen()
                2 -> FavoriteScreen()
                3 -> ProfileScreen()
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text("View All", color = Color.Gray, fontSize = 14.sp)
    }
}

data class Dish(val name: String, val calories: String, val price: String, val imageRes: Int)

@Composable
fun DishRow(dishes: List<Dish>) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(dishes) { dish ->
            Column(
                modifier = Modifier
                    .width(160.dp)
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(8.dp)
            ) {
                Image(
                    painter = painterResource(id = dish.imageRes),
                    contentDescription = dish.name,
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(dish.name, fontWeight = FontWeight.Bold)
                Text(dish.calories, color = Color.Gray, fontSize = 12.sp)
                Text(dish.price, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

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

@Composable
fun HomeScreen() {
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
                value = "",
                onValueChange = {},
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                placeholder = { Text("Search dishes") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            SectionTitle(title = "Popular dishes")
            DishRow(
                dishes = listOf(
                    Dish("Avocado Toast", "245 kcal", "$45", R.drawable.avocado_toast),
                    Dish("Italian Salad", "380 kcal", "$65", R.drawable.italian_salad)
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            SectionTitle(title = "Recipe of the week")
            DishRow(
                dishes = listOf(
                    Dish("Poached Egg", "245 kcal", "$55", R.drawable.poached_egg),
                    Dish("Cooked Noodles", "245 kcal", "$55", R.drawable.cooked_noodles)
                )
            )

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

@Composable fun ExploreScreen() { Text("Explore Screen", modifier = Modifier.padding(16.dp)) }
@Composable fun FavoriteScreen() { Text("Favorite Screen", modifier = Modifier.padding(16.dp)) }
@Composable fun ProfileScreen() { Text("Profile Screen", modifier = Modifier.padding(16.dp)) }


