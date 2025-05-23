package com.example.chefjuna

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.clickable
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.chefjuna.ui.theme.ChefJunaTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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
    var currentScreen by remember { mutableStateOf("home") }
    var selectedDish by remember { mutableStateOf<Dish?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    val favoriteDishes = remember { mutableStateListOf<Dish>() }
    var showFavoriteToast by remember { mutableStateOf(false) }
    var favoriteToastMessage by remember { mutableStateOf("") }

    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (currentScreen != "recipe_detail") {
                BottomNavigationBar(
                    selectedIndex = selectedIndex,
                    onItemSelected = {
                        selectedIndex = it
                        when (it) {
                            0 -> {
                                currentScreen = "home"
                                navController.navigate("home") {
                                    popUpTo("home") { inclusive = true }
                                }
                            }
                            1 -> {
                                currentScreen = "explore"
                                navController.navigate("explore") {
                                    popUpTo("home")
                                }
                            }
                            2 -> {
                                currentScreen = "favorite"
                                navController.navigate("favorite") {
                                    popUpTo("home")
                                }
                            }
                            3 -> {
                                currentScreen = "profile"
                                navController.navigate("profile") {
                                    popUpTo("home")
                                }
                            }
                        }
                    }
                )
            }
        },
        snackbarHost = {
            if (showFavoriteToast) {
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                    action = {
                        TextButton(onClick = { showFavoriteToast = false }) {
                            Text("Dismiss")
                        }
                    }
                ) {
                    Text(favoriteToastMessage)
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                currentScreen = "home"
                HomeScreen(
                    onDishClick = { dish ->
                        selectedDish = dish
                        currentScreen = "recipe_detail"
                        navController.navigate("recipe_detail")
                    },
                    searchQuery = searchQuery,
                    onSearchChanged = { query ->
                        searchQuery = query
                        isSearching = query.isNotEmpty()
                    },
                    isSearching = isSearching
                )
            }
            composable("recipe_detail") {
                RecipeDetailScreen(
                    dish = selectedDish ?: Dish("Avocado Toast", "245 kcal", "$45", R.drawable.avocado_toast),
                    onBackClick = {
                        currentScreen = "home"
                        navController.popBackStack()
                    },
                    favoriteDishes = favoriteDishes,
                    onToggleFavorite = { dish ->
                        if (favoriteDishes.contains(dish)) {
                            favoriteDishes.remove(dish)
                            favoriteToastMessage = "Removed from favorites"
                        } else {
                            favoriteDishes.add(dish)
                            favoriteToastMessage = "Added to favorites"
                        }
                        showFavoriteToast = true
                    }
                )
            }
            composable("explore") {
                currentScreen = "explore"
                ExploreScreen()
            }
            composable("favorite") {
                currentScreen = "favorite"
                FavoriteScreen(
                    favoriteDishes = favoriteDishes,
                    onDishClick = { dish ->
                        selectedDish = dish
                        currentScreen = "recipe_detail"
                        navController.navigate("recipe_detail")
                    }
                )
            }
            composable("profile") {
                currentScreen = "profile"
                ProfileScreen()
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

data class Ingredient(val name: String, val imageRes: Int)

@Composable
fun DishRow(dishes: List<Dish>, onDishClick: (Dish) -> Unit) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(dishes) { dish ->
            Column(
                modifier = Modifier
                    .width(160.dp)
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(8.dp)
                    .clickable(onClick = { onDishClick(dish) })
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
fun RecipeDetailScreen(
    dish: Dish,
    onBackClick: () -> Unit,
    favoriteDishes: SnapshotStateList<Dish>,
    onToggleFavorite: (Dish) -> Unit
) {
    var quantity by remember { mutableStateOf(1) }
    val isFavorite = favoriteDishes.contains(dish)

    val ingredients = listOf(
        Ingredient("Tomato", R.drawable.tomato),
        Ingredient("Mushroom", R.drawable.mushroom),
        Ingredient("Lettuce", R.drawable.lettuce),
        Ingredient("Cucumber", R.drawable.cucumber),
        Ingredient("Egg", R.drawable.egg)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header with curved green background
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .background(
                        color = Color(0xFF508130),
                        shape = RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)
                    )
            ) {
                // Top bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }

                    Row {
                        // Favorite button
                        IconButton(onClick = { onToggleFavorite(dish) }) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                                tint = Color.White
                            )
                        }

                        IconButton(onClick = { /* Search action */ }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = { /* Grid view action */ }) {
                            Icon(
                                imageVector = Icons.Outlined.GridView,
                                contentDescription = "Grid view",
                                tint = Color.White
                            )
                        }
                    }
                }

                // Recipe title
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .padding(bottom = 80.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = dish.name,
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${dish.calories} - 300 g",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }

            // Recipe image overlapping the green background
            Box(modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-70).dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = dish.imageRes),
                    contentDescription = dish.name,
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                )

                // Decorative elements
                Image(
                    painter = painterResource(id = R.drawable.carrot),
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .offset(x = (-80).dp, y = (-40).dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.leaf),
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .offset(x = (80).dp, y = (40).dp)
                )
            }

            // Price and quantity
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .offset(y = (-40).dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Color(0xFFF5F5F5)
                ) {
                    Text(
                        text = dish.price,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Color(0xFFF5F5F5), RoundedCornerShape(24.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    IconButton(
                        onClick = { if (quantity > 1) quantity-- },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Decrease",
                            tint = Color.Gray
                        )
                    }

                    Text(
                        text = quantity.toString(),
                        modifier = Modifier.padding(horizontal = 8.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )

                    IconButton(
                        onClick = { quantity++ },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Increase",
                            tint = Color.Gray
                        )
                    }
                }
            }

            // Ingredients section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .offset(y = (-20).dp)
            ) {
                Text(
                    text = "Ingredients",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(ingredients) { ingredient ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = Color(0xFFF5F5F5),
                                modifier = Modifier.size(64.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = ingredient.imageRes),
                                    contentDescription = ingredient.name,
                                    modifier = Modifier
                                        .padding(12.dp)
                                        .fillMaxSize()
                                )
                            }
                        }
                    }
                }
            }

            // Description section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "Description",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Fresh avocado on toasted sourdough bread, topped with cherry tomatoes, microgreens, and a sprinkle of sea salt. A healthy and delicious breakfast option that's quick to prepare and packed with nutrients.",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    lineHeight = 24.sp
                )
            }
        }

        // Floating action button
        FloatingActionButton(
            onClick = { /* Add to cart action */ },
            containerColor = Color(0xFF508130),
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add to cart"
            )
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
fun HomeScreen(
    onDishClick: (Dish) -> Unit,
    searchQuery: String,
    onSearchChanged: (String) -> Unit,
    isSearching: Boolean
) {
    // Define all dishes
    val allDishes = listOf(
        Dish("Avocado Toast", "245 kcal", "$9.99", R.drawable.avocado_toast),
        Dish("Italian Salad", "380 kcal", "$12.50", R.drawable.italian_salad),
        Dish("Poached Egg", "180 kcal", "$8.75", R.drawable.poached_egg),
        Dish("Cooked Noodles", "420 kcal", "$11.25", R.drawable.cooked_noodles)
    )

    // Filter dishes based on search query
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
            // Display search results
            if (filteredDishes.isNotEmpty()) {
                SectionTitle(title = "Search Results")
                DishRow(dishes = filteredDishes, onDishClick = onDishClick)
            } else {
                Text("No dishes found matching \"$searchQuery\"",
                    modifier = Modifier.padding(vertical = 16.dp))
            }
        } else {
            // Display normal content when not searching
            SectionTitle(title = "Popular dishes")
            DishRow(
                dishes = listOf(
                    Dish("Avocado Toast", "245 kcal", "$9.99", R.drawable.avocado_toast),
                    Dish("Italian Salad", "380 kcal", "$12.50", R.drawable.italian_salad)
                ),
                onDishClick = onDishClick
            )

            Spacer(modifier = Modifier.height(24.dp))

            SectionTitle(title = "Recipe of the week")
            DishRow(
                dishes = listOf(
                    Dish("Poached Egg", "180 kcal", "$8.75", R.drawable.poached_egg),
                    Dish("Cooked Noodles", "420 kcal", "$11.25", R.drawable.cooked_noodles)
                ),
                onDishClick = onDishClick
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

@Composable
fun ExploreScreen() {
    Text("Explore Screen", modifier = Modifier.padding(16.dp))
}

@Composable
fun FavoriteScreen(favoriteDishes: List<Dish>, onDishClick: (Dish) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Your Favorite Recipes",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF508130),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (favoriteDishes.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "You haven't added any favorites yet",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(favoriteDishes) { dish ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(16.dp))
                            .clickable { onDishClick(dish) }
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = dish.imageRes),
                            contentDescription = dish.name,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(
                                text = dish.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = dish.calories,
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = dish.price,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Icon(
                            imageVector = Icons.Outlined.Favorite,
                            contentDescription = "Favorite",
                            tint = Color(0xFFE91E63),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileScreen() {
    Text("Profile Screen", modifier = Modifier.padding(16.dp))
}