package com.example.chefjuna.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chefjuna.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen() {
    var selectedCategory by remember { mutableStateOf<ExploreCategory?>(null) }
    var searchQuery by remember { mutableStateOf("") }

    if (selectedCategory != null) {
        CategoryDetailScreen(
            category = selectedCategory!!,
            onBackClick = { selectedCategory = null }
        )
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F9FA)),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Discover Amazing Recipes",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E7D32)
                    )
                    Text(
                        text = "Explore culinary delights from around the world",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search recipes, ingredients...") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF4CAF50),
                            unfocusedBorderColor = Color(0xFFE0E0E0)
                        )
                    )
                }
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(horizontal = 16.dp)
                        .clickable { },
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                ) {
                    Box {
                        Image(
                            painter = painterResource(id = R.drawable.beef_rendang),
                            contentDescription = "Featured Recipe",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.7f)
                                        )
                                    )
                                )
                        )

                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Chef's Special",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "Beef Rendang Authentic",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(top = 4.dp)
                            ) {
                                Icon(
                                    Icons.Default.Star,
                                    contentDescription = null,
                                    tint = Color(0xFFFFD700),
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "4.9 • 3 hours • Indonesian",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(start = 4.dp)
                                )
                            }
                        }
                    }
                }
            }

            item {
                Column {
                    Text(
                        text = "Browse Categories",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = Color(0xFF1B5E20)
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        items(getCategoryList()) { category ->
                            CategoryCard(
                                category = category,
                                onClick = { selectedCategory = category }
                            )
                        }
                    }
                }
            }

            item {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Trending Now 🔥",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1B5E20)
                        )
                        TextButton(onClick = { }) {
                            Text("See All", color = Color(0xFF4CAF50))
                        }
                    }

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(getTrendingRecipes()) { recipe ->
                            TrendingRecipeCard(recipe)
                        }
                    }
                }
            }

            item {
                Column {
                    Text(
                        text = "Chef Juna Recommends",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = Color(0xFF1B5E20)
                    )

                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        getChefRecommendations().forEach { recipe ->
                            ChefRecommendationCard(recipe)
                        }
                    }
                }
            }

            item {
                Column {
                    Text(
                        text = "Quick & Easy (Under 30 min)",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = Color(0xFF1B5E20)
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        items(getQuickRecipes()) { recipe ->
                            QuickRecipeCard(recipe)
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDetailScreen(
    category: ExploreCategory,
    onBackClick: () -> Unit
) {
    val recipes = getRecipesByCategory(category.name)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "${category.name} Recipes",
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,
                titleContentColor = Color(0xFF2E7D32)
            )
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box {
                Image(
                    painter = painterResource(id = category.imageRes),
                    contentDescription = category.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.4f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${category.name} Cuisine",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Recipe Count
        Text(
            text = "${recipes.size} recipes found",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = Color.Gray,
            fontSize = 14.sp
        )

        // Recipes Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(recipes) { recipe ->
                CategoryRecipeCard(recipe)
            }
        }
    }
}

@Composable
fun CategoryCard(
    category: ExploreCategory,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(80.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box {
            Image(
                painter = painterResource(id = category.imageRes),
                contentDescription = category.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = category.name,
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun CategoryRecipeCard(recipe: Recipe) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Image(
                painter = painterResource(id = recipe.imageRes),
                contentDescription = recipe.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = recipe.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = "${recipe.rating}",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 4.dp)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = recipe.time,
                        fontSize = 12.sp,
                        color = Color(0xFF4CAF50),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun TrendingRecipeCard(recipe: Recipe) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .clickable { },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Box {
                Image(
                    painter = painterResource(id = recipe.imageRes),
                    contentDescription = recipe.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )

                Surface(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = Color.Red.copy(alpha = 0.9f)
                ) {
                    Text(
                        text = "HOT",
                        color = Color.White,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = recipe.name,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = "${recipe.rating} • ${recipe.time}",
                        fontSize = 10.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ChefRecommendationCard(recipe: ChefRecommendation) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row {
            Image(
                painter = painterResource(id = recipe.imageRes),
                contentDescription = recipe.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp))
            )

            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .weight(1f)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Chef Juna",
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                    )
                    Text(
                        text = "Chef Juna",
                        fontSize = 10.sp,
                        color = Color(0xFF4CAF50),
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                Text(
                    text = recipe.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Text(
                    text = recipe.description,
                    fontSize = 11.sp,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 2.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 6.dp)
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = "${recipe.rating} • ${recipe.difficulty} • ${recipe.time}",
                        fontSize = 10.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun QuickRecipeCard(recipe: Recipe) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .clickable { },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Box {
                Image(
                    painter = painterResource(id = recipe.imageRes),
                    contentDescription = recipe.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                )

                Surface(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(6.dp),
                    shape = RoundedCornerShape(6.dp),
                    color = Color(0xFF4CAF50)
                ) {
                    Text(
                        text = "QUICK",
                        color = Color.White,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                    )
                }
            }

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = recipe.name,
                    fontWeight = FontWeight.Medium,
                    fontSize = 11.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = recipe.time,
                    fontSize = 9.sp,
                    color = Color(0xFF4CAF50),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }
}

data class ExploreCategory(val name: String, val imageRes: Int)
data class Recipe(val name: String, val imageRes: Int, val rating: Float, val time: String)
data class ChefRecommendation(
    val name: String,
    val imageRes: Int,
    val description: String,
    val rating: Float,
    val difficulty: String,
    val time: String
)

fun getCategoryList(): List<ExploreCategory> {
    return listOf(
        ExploreCategory("Italian", R.drawable.truffle_pasta),
        ExploreCategory("Asian", R.drawable.korean_ramen),
        ExploreCategory("Mexican", R.drawable.avocado_toast),
        ExploreCategory("Desserts", R.drawable.lava_cake),
        ExploreCategory("Seafood", R.drawable.category_vegetarian),
        ExploreCategory("Vegetarian", R.drawable.caprese_salad),
        ExploreCategory("BBQ", R.drawable.chicken_teriyaki),
        ExploreCategory("Healthy", R.drawable.garlic_fried_rice)
    )
}

fun getTrendingRecipes(): List<Recipe> {
    return listOf(
        Recipe("Spicy Korean Ramen", R.drawable.korean_ramen, 4.8f, "25 min"),
        Recipe("Avocado Toast Deluxe", R.drawable.avocado_toast, 4.6f, "10 min"),
        Recipe("Butter Chicken", R.drawable.butter_chicken, 4.9f, "45 min"),
        Recipe("Chocolate Lava Cake", R.drawable.lava_cake, 4.7f, "30 min"),
        Recipe("Thai Green Curry", R.drawable.thai_curry, 4.8f, "35 min")
    )
}

fun getChefRecommendations(): List<ChefRecommendation> {
    return listOf(
        ChefRecommendation(
            "Beef Rendang Authentic",
            R.drawable.beef_rendang,
            "My grandmother's secret recipe from Padang. Rich, complex flavors that take time to develop perfectly.",
            4.9f,
            "Expert",
            "3 hours"
        ),
        ChefRecommendation(
            "Truffle Pasta Supreme",
            R.drawable.truffle_pasta,
            "Restaurant-quality pasta with real truffle oil and parmesan. A luxurious dining experience at home.",
            4.8f,
            "Intermediate",
            "25 min"
        ),
        ChefRecommendation(
            "Crispy Duck Confit",
            R.drawable.bebek,
            "French technique meets Indonesian spices. The skin is incredibly crispy, meat falls off the bone.",
            4.9f,
            "Expert",
            "4 hours"
        )
    )
}

fun getQuickRecipes(): List<Recipe> {
    return listOf(
        Recipe("Garlic Fried Rice", R.drawable.garlic_fried_rice, 4.5f, "15 min"),
        Recipe("Caprese Salad", R.drawable.caprese_salad, 4.4f, "5 min"),
        Recipe("Chicken Teriyaki", R.drawable.chicken_teriyaki, 4.7f, "20 min"),
        Recipe("Pancakes Stack", R.drawable.pancakes, 4.6f, "12 min"),
        Recipe("Caesar Salad", R.drawable.category_vegetarian, 4.5f, "8 min")
    )
}

fun getRecipesByCategory(categoryName: String): List<Recipe> {
    return when (categoryName) {
        "Italian" -> listOf(
            Recipe("Truffle Pasta Supreme", R.drawable.truffle_pasta, 4.8f, "25 min"),
            Recipe("Margherita Pizza", R.drawable.beef_rendang, 4.7f, "30 min"),
            Recipe("Caprese Salad", R.drawable.caprese_salad, 4.4f, "5 min"),
            Recipe("Chicken Parmigiana", R.drawable.chicken_teriyaki, 4.6f, "40 min"),
            Recipe("Carbonara Pasta", R.drawable.garlic_fried_rice, 4.5f, "20 min"),
            Recipe("Tiramisu Delight", R.drawable.lava_cake, 4.9f, "45 min")
        )
        "Asian" -> listOf(
            Recipe("Spicy Korean Ramen", R.drawable.korean_ramen, 4.8f, "25 min"),
            Recipe("Thai Green Curry", R.drawable.thai_curry, 4.8f, "35 min"),
            Recipe("Chicken Teriyaki", R.drawable.chicken_teriyaki, 4.7f, "20 min"),
            Recipe("Beef Rendang Authentic", R.drawable.beef_rendang, 4.9f, "3 hours"),
            Recipe("Garlic Fried Rice", R.drawable.garlic_fried_rice, 4.5f, "15 min"),
            Recipe("Duck Confit Asian Style", R.drawable.bebek, 4.8f, "4 hours")
        )
        "Mexican" -> listOf(
            Recipe("Avocado Toast Deluxe", R.drawable.avocado_toast, 4.6f, "10 min"),
            Recipe("Spicy Chicken Tacos", R.drawable.chicken_teriyaki, 4.5f, "25 min"),
            Recipe("Mexican Rice Bowl", R.drawable.garlic_fried_rice, 4.4f, "20 min"),
            Recipe("Guacamole Supreme", R.drawable.avocado_toast, 4.7f, "8 min")
        )
        "Desserts" -> listOf(
            Recipe("Chocolate Lava Cake", R.drawable.lava_cake, 4.7f, "30 min"),
            Recipe("Tiramisu Classic", R.drawable.truffle_pasta, 4.8f, "45 min"),
            Recipe("Pancakes Stack", R.drawable.pancakes, 4.6f, "12 min"),
            Recipe("Fruit Tart Deluxe", R.drawable.caprese_salad, 4.5f, "35 min")
        )
        "Seafood" -> listOf(
            Recipe("Grilled Salmon", R.drawable.beef_rendang, 4.6f, "20 min"),
            Recipe("Seafood Paella", R.drawable.garlic_fried_rice, 4.8f, "45 min"),
            Recipe("Fish & Chips", R.drawable.chicken_teriyaki, 4.5f, "30 min"),
            Recipe("Shrimp Scampi", R.drawable.truffle_pasta, 4.7f, "15 min")
        )
        "Vegetarian" -> listOf(
            Recipe("Caprese Salad", R.drawable.caprese_salad, 4.4f, "5 min"),
            Recipe("Vegetable Curry", R.drawable.thai_curry, 4.6f, "30 min"),
            Recipe("Quinoa Power Bowl", R.drawable.garlic_fried_rice, 4.5f, "15 min"),
            Recipe("Stuffed Bell Peppers", R.drawable.avocado_toast, 4.7f, "40 min")
        )
        "BBQ" -> listOf(
            Recipe("BBQ Chicken Wings", R.drawable.chicken_teriyaki, 4.7f, "35 min"),
            Recipe("Grilled Beef Ribs", R.drawable.beef_rendang, 4.8f, "2 hours"),
            Recipe("Smoked Duck", R.drawable.bebek, 4.9f, "3 hours"),
            Recipe("BBQ Pork Belly", R.drawable.truffle_pasta, 4.6f, "1 hour")
        )
        "Healthy" -> listOf(
            Recipe("Quinoa Salad Bowl", R.drawable.caprese_salad, 4.5f, "10 min"),
            Recipe("Grilled Chicken Breast", R.drawable.chicken_teriyaki, 4.4f, "15 min"),
            Recipe("Green Smoothie Bowl", R.drawable.avocado_toast, 4.6f, "5 min"),
            Recipe("Steamed Fish", R.drawable.garlic_fried_rice, 4.3f, "20 min")
        )
        else -> getTrendingRecipes()
    }
}